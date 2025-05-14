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
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType

@RunWith(AndroidJUnit4::class)
class ConfirmNoNonChippedIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val analytics: SelectDocAnalytics = mock()
    val context: Context = ApplicationProvider.getApplicationContext()
    val confirmButton = hasText(context.getString(R.string.confirm_nononchippedid_confirmbutton))

    @Test
    fun `when confirm is tapped and journey is MAM, it navigates to Confirm Abort Mobile screen`() {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.MobileAppMobile,
                    ),
            )

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(
            HandbackDestinations.ConfirmAbortMobile()
        )
    }

    @Test
    fun `when confirm is tapped and journey is DAD, it navigates to Confirm Abort Desktop screen`() {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.DesktopAppDesktop,
                    ),
            )

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(
            HandbackDestinations.ConfirmAbortDesktop(),
        )
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
