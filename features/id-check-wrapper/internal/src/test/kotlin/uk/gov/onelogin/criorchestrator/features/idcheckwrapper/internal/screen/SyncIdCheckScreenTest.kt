package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
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
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class SyncIdCheckScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val launchButton = hasText("Launch ID Check SDK")
    private val happyPathOption = hasText("Happy path")

    private val navController: NavController = mock()

    private fun createViewModel(
        session: Session = Session.createMobileAppMobileInstance(),
        biometricTokenResult: BiometricTokenResult =
            BiometricTokenResult.Success(
                BiometricToken.createTestToken(),
            ),
        readerDelay: Long = 0,
    ) = SyncIdCheckViewModel(
        launcherDataReader =
            LauncherDataReader(
                FakeSessionStore(
                    session = session,
                ),
                biometricTokenReader =
                    StubBiometricTokenReader(
                        biometricTokenResult = biometricTokenResult,
                        delay = readerDelay,
                    ),
            ),
        logger = SystemLogger(),
        analytics = mock(),
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

    @Test
    fun `when loading state is receive, loading progress indicator is displayed`() =
        runTest {
            val viewModel =
                createViewModel(
                    readerDelay = 1000,
                )

            val loadingState = viewModel.state.first()
            assertEquals(SyncIdCheckState.Loading, loadingState)

            composeTestRule.setScreenContent(viewModel = viewModel)

            composeTestRule.waitForIdle()

            composeTestRule
                .onNode(hasText("Loading"))
                .assertIsDisplayed()
        }

    @Test
    fun `when data launcher returns recoverable error, navigate to recoverable error screen`() =
        runTest {
            val viewModel =
                createViewModel(
                    biometricTokenResult = BiometricTokenResult.Offline,
                )

            viewModel.actions.test {
                composeTestRule.setScreenContent(viewModel = viewModel)

                assertEquals(SyncIdCheckAction.NavigateToRecoverableError, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            composeTestRule.waitForIdle()

            verify(navController).navigate(ErrorDestinations.RecoverableError)
        }

    @Test
    fun `when data launcher returns unrecoverable error, navigate to unrecoverable error screen`() =
        runTest {
            val viewModel =
                createViewModel(
                    biometricTokenResult =
                        BiometricTokenResult.Error(
                            Exception("error"),
                            400,
                        ),
                )

            viewModel.actions.test {
                composeTestRule.setScreenContent(viewModel = viewModel)

                assertEquals(SyncIdCheckAction.NavigateToUnrecoverableError, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

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
