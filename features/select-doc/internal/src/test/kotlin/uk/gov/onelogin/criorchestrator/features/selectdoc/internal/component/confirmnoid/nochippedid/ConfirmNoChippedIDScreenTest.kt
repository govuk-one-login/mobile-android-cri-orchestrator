package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.component.confirmnoid.nochippedid

import android.content.Context
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

@RunWith(AndroidJUnit4::class)
class ConfirmNoChippedIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val navController: NavController = mock()
    private val analytics: SelectDocAnalytics = mock()
    private val fakeSessionStoreDisableBrp = FakeSessionStore(Session.createTestInstance(disableBrpJourney = true))
    private val fakeSessionStoreDefault = FakeSessionStore(Session.createTestInstance())
    val context: Context = ApplicationProvider.getApplicationContext()
    val confirmButton = hasText(context.getString(R.string.confirm_nochippedid_confirmbutton))
    val brpBulletItem = hasText(context.getString(R.string.confirm_nochippedid_bullet_3))

    @Test
    fun `when confirm is tapped and journey is DAD, it navigates to Confirm Abort Desktop`() {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.DesktopAppDesktop,
                    ),
                sessionStore = fakeSessionStoreDefault,
            )

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(AbortDestinations.ConfirmAbortDesktop)
    }

    @Test
    fun `when confirm is tapped and journey is MAM, it navigates to Confirm Abort Mobile`() {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.MobileAppMobile("https://example.com"),
                    ),
                sessionStore = fakeSessionStoreDefault,
            )

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(AbortDestinations.ConfirmAbortMobile)
    }

    @Test
    fun `when disableBrpJourney flag is set to false, BRP bullet point is displayed`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.MobileAppMobile("https://example.com"),
                    ),
                sessionStore = fakeSessionStoreDefault,
            )

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(brpBulletItem)
            .assertExists()
    }

    @Test
    fun `when disableBrpJourney flag is set to true, BRP bullet point is not displayed`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.MobileAppMobile("https://example.com"),
                    ),
                sessionStore = fakeSessionStoreDisableBrp,
            )

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(brpBulletItem)
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.setContent(viewModel: ConfirmNoChippedIDViewModel) {
        composeTestRule.setContent {
            ConfirmNoChippedIDScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}
