package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

import android.content.Context
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType

@RunWith(AndroidJUnit4::class)
class ConfirmNoNonChippedIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val analytics: SelectDocAnalytics = mock()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val confirmButton = hasText(context.getString(R.string.confirm_nononchippedid_confirmbutton))
    private val getJourneyType = StubGetJourneyType()
    private val viewModel by lazy {
        ConfirmNoNonChippedIDViewModel(
            analytics = analytics,
            getJourneyType = getJourneyType,
        )
    }

    @Test
    fun `when confirm is tapped and journey is MAM, it navigates to Confirm Abort Mobile screen`() {
        getJourneyType.journeyType = JourneyType.MobileAppMobile("https://example.com")

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(AbortDestinations.ConfirmAbortMobile)
    }

    @Test
    fun `when confirm is tapped and journey is DAD, it navigates to Confirm Abort Desktop screen`() {
        getJourneyType.journeyType = JourneyType.DesktopAppDesktop

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(AbortDestinations.ConfirmAbortDesktop)
    }

    private fun ComposeContentTestRule.setContent(viewModel: ConfirmNoNonChippedIDViewModel) {
        composeTestRule.setContent {
            ConfirmNoNonChippedIDScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}
