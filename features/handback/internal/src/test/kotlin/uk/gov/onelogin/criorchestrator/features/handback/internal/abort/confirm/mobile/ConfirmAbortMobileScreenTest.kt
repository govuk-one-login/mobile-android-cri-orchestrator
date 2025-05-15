import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileConstants
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class ConfirmAbortMobileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val continueButton = hasTextStartingWith(context.getString(ConfirmAbortMobileConstants.buttonId))
    private val webNavigator = FakeWebNavigator()

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
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ConfirmAbortMobileScreen(
                viewModel = viewModel,
                webNavigator = webNavigator,
            )
        }
    }

    @Test
    fun `when continue is clicked, it navigates to return to gov uk`() {
        composeTestRule
            .onNode(continueButton)
            .performClick()

        assertEquals(REDIRECT_URI, webNavigator.openUrl)
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
