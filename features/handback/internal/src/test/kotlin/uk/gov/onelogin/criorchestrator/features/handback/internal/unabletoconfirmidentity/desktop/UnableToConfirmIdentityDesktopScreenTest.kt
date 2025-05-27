package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.desktop

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession

@RunWith(AndroidJUnit4::class)
class UnableToConfirmIdentityDesktopScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val navController: NavController = mock()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val continueButton =
        hasTextStartingWith(context.getString(UnableToConfirmIdentityDesktopConstants.buttonId))
    private val abortSession = StubAbortSession(AbortSession.Result.Success)

    private val viewModel by lazy {
        UnableToConfirmIdentityDesktopViewModel(
            analytics = mock(),
            abortSession = abortSession,
        )
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            UnableToConfirmIdentityDesktopScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when screen is launched, the screen displays desired content`() {
        composeTestRule
            .onNodeWithText(
                context.getString(R.string.handback_unabletoconfirmidentity_body),
            ).assertExists()
    }

    @Test
    fun `when continue is clicked, initially the screen displays loading screen`() {
        abortSession.delay = 300L
        composeTestRule
            .onNode(continueButton)
            .performClick()

        composeTestRule.onNodeWithText("Loading").assertExists()
    }

    @Test
    fun `when continue is clicked, given a successful abort call, it navigates to return to gov uk`() {
        composeTestRule
            .onNode(continueButton)
            .performClick()

        verify(navController).navigate(AbortDestinations.AbortedReturnToDesktopWeb)
    }

    @Test
    fun `when continue is clicked, given an unsuccessful call, it navigates to unrecoverable error`() {
        abortSession.result = AbortSession.Result.Error.Unrecoverable(exception = Exception("exception"))
        composeTestRule
            .onNode(continueButton)
            .performClick()

        verify(navController).navigate(HandbackDestinations.UnrecoverableError)
    }

    @Test
    fun `when continue is clicked, given user is offline, it navigates to offline error`() {
        abortSession.result = AbortSession.Result.Error.Offline
        composeTestRule
            .onNode(continueButton)
            .performClick()

        verify(navController).navigate(ErrorDestinations.RecoverableError)
    }
}
