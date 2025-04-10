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
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
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
                ),
            logger = SystemLogger(),
        )

    @Test
    fun `given manual laucher and MAM session, when happy path launched, it navigates to mobile handback`() {
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
    fun `given manual laucher and DAD session, when happy path launched, it navigates to desktop handback`() {
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

    private fun ComposeContentTestRule.setScreenContent(viewModel: SyncIdCheckViewModel = createViewModel()) =
        setContent {
            SyncIdCheckScreen(
                documentVariety = DocumentVariety.NFC_PASSPORT,
                viewModel = viewModel,
                navController = navController,
            )
        }
}
