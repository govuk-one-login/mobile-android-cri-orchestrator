package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config.createTestInstance
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

    private val launchButton = "Launch ID Check SDK"
    private val happyPathOption = "Happy path"
    private val unhappyPathOption = "Confirm identity another way"

    private val navController: NavController = mock()

    private var session: Session = Session.createMobileAppMobileInstance()
    private var enableManualLauncher = false

    private val viewModel by lazy {
        SyncIdCheckViewModel.createTestInstance(
            launcherDataReader =
                LauncherDataReader(
                    FakeSessionStore(
                        session = session,
                    ),
                    biometricTokenReader =
                        StubBiometricTokenReader(
                            biometricTokenResult =
                                BiometricTokenResult.Success(
                                    BiometricToken.createTestToken(),
                                ),
                        ),
                ),
            configStore =
                FakeConfigStore(
                    initialConfig =
                        Config.createTestInstance(
                            enableManualLauncher = enableManualLauncher,
                        ),
                ),
        )
    }

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
        configStore =
            FakeConfigStore(
                initialConfig =
                    Config.createTestInstance(
                        enableManualLauncher = true,
                    ),
            ),
    )

    @Test
    fun `given manual launcher and MAM session, when happy path launched, it navigates to mobile handback`() {
        enableManualLauncher = true
        session = Session.createMobileAppMobileInstance()
        composeTestRule.setScreenContent(viewModel)

        composeTestRule.selectOption(happyPathOption)
        composeTestRule.clickLaunchButton()

        composeTestRule.waitForIdle()

        verify(navController).navigate(
            HandbackDestinations.ReturnToMobileWeb,
        )
    }

    @Test
    fun `given manual launcher and DAD session, when happy path launched, it navigates to desktop handback`() {
        enableManualLauncher = true
        session = Session.createDesktopAppDesktopInstance()
        composeTestRule.setScreenContent(viewModel)

        composeTestRule.selectOption(happyPathOption)
        composeTestRule.clickLaunchButton()

        composeTestRule.waitForIdle()

        verify(navController).navigate(
            HandbackDestinations.ReturnToDesktopWeb,
        )
    }

    @Test
    fun `manual launch with MAM session, when launch unhappy path, navigates to confirm abort MAM`() {
        enableManualLauncher = true
        session = Session.createMobileAppMobileInstance()
        composeTestRule.setScreenContent(viewModel)

        composeTestRule.selectOption(unhappyPathOption)
        composeTestRule.clickLaunchButton()

        composeTestRule.waitForIdle()

        verify(navController).navigate(
            HandbackDestinations.ConfirmAbort,
        )
    }

    @Test
    @Suppress("")
    fun `manual launch with DAD session, when launch unhappy path, navigates to confirm abort DAD`() {
        enableManualLauncher = true
        session = Session.createDesktopAppDesktopInstance()
        composeTestRule.setScreenContent(viewModel)

        composeTestRule.selectOption(unhappyPathOption)
        composeTestRule.clickLaunchButton()

        composeTestRule.waitForIdle()

        verify(navController).navigate(
            HandbackDestinations.ConfirmAbort,
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

            composeTestRule.setScreenContent(viewModel = viewModel)

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
                            Exception("Test error"),
                        ),
                )

            composeTestRule.setScreenContent(viewModel = viewModel)

            composeTestRule.waitForIdle()

            verify(navController).navigate(HandbackDestinations.UnrecoverableError)
        }

    private fun ComposeContentTestRule.selectOption(text: String) =
        onNodeWithText(text, useUnmergedTree = true)
            .performScrollTo()
            .performClick()

    private fun ComposeContentTestRule.clickLaunchButton() =
        onNodeWithText(launchButton)
            .performClick()

    private fun ComposeContentTestRule.setScreenContent(viewModel: SyncIdCheckViewModel) =
        setContent {
            SyncIdCheckScreen(
                documentVariety = DocumentVariety.NFC_PASSPORT,
                viewModel = viewModel,
                navController = navController,
            )
        }
}
