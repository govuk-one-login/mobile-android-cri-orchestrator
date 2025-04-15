package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

@RunWith(AndroidJUnit4::class)
class SyncIdCheckScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val launchButton = hasText("Launch ID Check SDK")
    private val happyPathOption = hasText("Happy path")

    private val navController: NavController = mock()

    private fun createViewModel(session: Session = Session.createMobileAppMobileInstance()) =
        SyncIdCheckViewModel(
            launcherDataReader =
                LauncherDataReader(
                    FakeSessionStore(
                        session = session,
                    ),
                    biometricTokenReader =
                        StubBiometricTokenReader(
                            BiometricTokenResult.Success(
                                BiometricToken.createTestToken(),
                            ),
                        ),
                ),
            logger = SystemLogger(),
        )

    @Test
    fun `given manual launcher and MAM session, when happy path launched, it navigates to mobile handback`() {
        val viewModel =
            createViewModel(
                session = Session.createMobileAppMobileInstance(),
            )
        composeTestRule.setScreenContent(
            viewModel = viewModel,
        )
        composeTestRule
            .onNode(happyPathOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(launchButton)
            .performClick()

        composeTestRule.waitForIdle()

        verify(navController).navigate(
            HandbackDestinations.ReturnToMobileWeb,
        )
    }

    @Test
    fun `given manual launcher and DAD session, when happy path launched, it navigates to desktop handback`() {
        val viewModel =
            createViewModel(
                session = Session.createDesktopAppDesktopInstance(),
            )
        composeTestRule.setScreenContent(
            viewModel = viewModel,
        )
        composeTestRule
            .onNode(happyPathOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(launchButton)
            .performClick()

        composeTestRule.waitForIdle()

        verify(navController).navigate(
            HandbackDestinations.ReturnToDesktopWeb,
        )
    }

    // AC2: Loading state
    @Test
    fun `when loading state is receive, loading progress indicator is displayed`() {
        val actionsFlow = MutableSharedFlow<SyncIdCheckAction>()
        val viewModel =
            mock<SyncIdCheckViewModel>().apply {
                whenever(actions).thenReturn(actionsFlow.asSharedFlow())
                whenever(state).thenReturn(MutableStateFlow(SyncIdCheckState.Loading))
            }

        composeTestRule.setScreenContent(
            viewModel = viewModel,
        )

        composeTestRule
            .onNode(hasText("Loading"))
            .assertIsDisplayed()
    }

    // AC4: User receives recoverable error on call
    @Test
    fun `when data launcher returns recoverable error, navigate to recoverable error screen`() =
        runTest {
            val actionsFlow = MutableSharedFlow<SyncIdCheckAction>()
            val viewModel =
                mock<SyncIdCheckViewModel>().apply {
                    whenever(actions).thenReturn(actionsFlow.asSharedFlow())
                    whenever(state).thenReturn(MutableStateFlow(SyncIdCheckState.Loading))
                }

            composeTestRule.setScreenContent(
                viewModel = viewModel,
            )

            actionsFlow.emit(SyncIdCheckAction.NavigateToRecoverableError)
            composeTestRule.waitForIdle()

            verify(navController).navigate(ErrorDestinations.RecoverableError)
        }

    // AC3: User receives unrecoverable error on call
    @Test
    fun `when data launcher returns unrecoverable error, navigate to unrecoverable error screen`() =
        runTest {
            val actionsFlow = MutableSharedFlow<SyncIdCheckAction>()
            val viewModel =
                mock<SyncIdCheckViewModel>().apply {
                    whenever(actions).thenReturn(actionsFlow.asSharedFlow())
                    whenever(state).thenReturn(MutableStateFlow(SyncIdCheckState.Loading))
                }

            composeTestRule.setScreenContent(
                viewModel = viewModel,
            )

            actionsFlow.emit(SyncIdCheckAction.NavigateToUnRecoverableError)
            composeTestRule.waitForIdle()

            verify(navController).navigate(HandbackDestinations.UnrecoverableError)
        }

    private fun ComposeContentTestRule.setScreenContent(viewModel: SyncIdCheckViewModel = createViewModel()) =
        setContent {
            SyncIdCheckScreen(
                documentVariety = DocumentVariety.NFC_PASSPORT,
                viewModel = viewModel,
                navController = navController,
            )
        }
}
