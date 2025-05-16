package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
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
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession

@RunWith(AndroidJUnit4::class)
class ConfirmAbortDesktopScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val navController: NavController = mock()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val continueButton = hasText(context.getString(ConfirmAbortDesktopConstants.buttonId))
    private val abortSession = StubAbortSession(AbortSession.Result.Success)

    private val viewModel by lazy {
        ConfirmAbortDesktopViewModel(
            analytics = mock(),
            abortSession = abortSession,
        )
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            ConfirmAbortDesktopWebScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when talkback is enabled, it reads out Gov dot UK correctly`() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val body =
            composeTestRule
                .onNode(hasText(context.getString(R.string.handback_confirmabort_body1)))
        body.assertContentDescriptionContains("Gov dot UK", true)
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
