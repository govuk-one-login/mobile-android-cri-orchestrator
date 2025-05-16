package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType

@RunWith(AndroidJUnit4::class)
class UnrecoverableErrorScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val button = hasText(context.getString(R.string.handback_unrecoverableerror_button))

    private val navController: NavController = mock()
    private val getJourneyType = StubGetJourneyType()

    private val viewModel =
        UnrecoverableErrorViewModel(
            analytics = mock(),
            getJourneyType = getJourneyType,
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            UnrecoverableErrorScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `given journey is MAM, when button is clicked, it navigates to abort flow`() {
        getJourneyType.journeyType = JourneyType.MobileAppMobile("https://example.com")
        composeTestRule
            .onNode(button)
            .performClick()

        verify(navController).navigate(eq(AbortDestinations.ConfirmAbortMobile), any<NavOptionsBuilder.() -> Unit>())
    }

    @Test
    fun `given journey is DAD, when button is clicked, it navigates to abort flow`() {
        getJourneyType.journeyType = JourneyType.DesktopAppDesktop
        composeTestRule
            .onNode(button)
            .performClick()

        verify(navController).navigate(eq(AbortDestinations.ConfirmAbortDesktop), any<NavOptionsBuilder.() -> Unit>())
    }
}
