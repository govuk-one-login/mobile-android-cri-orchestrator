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
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileConstants
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

@RunWith(AndroidJUnit4::class)
class ConfirmAbortMobileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val continueButton = hasTextStartingWith(context.getString(ConfirmAbortMobileConstants.buttonId))
    private val navController = mock<NavController>()

    private val session =
        Session.createTestInstance(
            redirectUri = REDIRECT_URI,
        )

    private val viewModel =
        ConfirmAbortMobileViewModel(
            analytics = mock(),
            sessionStore =
                FakeSessionStore(
                    session = session,
                ),
            abortSession = StubAbortSession(),
            logger = mock(),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ConfirmAbortMobileScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when continue is clicked, it navigates to redirect to mobile web holder screen`() {
        composeTestRule
            .onNode(continueButton)
            .performClick()

        verify(navController).navigate(
            AbortDestinations.AbortedRedirectToMobileWebHolder("https://example/redirect"),
        )
    }

    @Test
    fun `when talkback is enabled, it reads out Gov dot UK correctly`() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val body =
            composeTestRule
                .onNode(hasText(context.getString(R.string.handback_confirmabort_body1)))
        body.assertContentDescriptionContains("Gov dot UK", true)
    }
}
