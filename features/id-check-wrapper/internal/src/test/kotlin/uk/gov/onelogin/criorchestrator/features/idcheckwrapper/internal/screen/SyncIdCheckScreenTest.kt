package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config.createTestInstance
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

    private var session: Session = Session.createMobileAppMobileInstance()
    private var enableManualLauncher = false

    private val viewModel by lazy {
        SyncIdCheckViewModel.createTestInstance(
            launcherDataReader =
                LauncherDataReader(
                    FakeSessionStore(
                        session = session,
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

    @Test
    fun `given manual launcher and MAM session, when happy path launched, it navigates to mobile handback`() {
        enableManualLauncher = true
        session = Session.createMobileAppMobileInstance()
        composeTestRule.setScreenContent()
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
        enableManualLauncher = true
        session = Session.createDesktopAppDesktopInstance()
        composeTestRule.setScreenContent()
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

    private fun ComposeContentTestRule.setScreenContent() =
        setContent {
            SyncIdCheckScreen(
                documentVariety = DocumentVariety.NFC_PASSPORT,
                viewModel = viewModel,
                navController = navController,
            )
        }
}
