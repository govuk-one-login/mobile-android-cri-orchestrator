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
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopConstants
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations

@RunWith(AndroidJUnit4::class)
class ConfirmAbortDesktopScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val navController: NavController = mock()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val continueButton = hasText(context.getString(ConfirmAbortDesktopConstants.buttonId))

    private val viewModel =
        ConfirmAbortDesktopViewModel(
            analytics = mock(),
        )

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
    fun `when continue is clicked, it navigates to return to gov uk`() {
        composeTestRule
            .onNode(continueButton)
            .performClick()

        verify(navController).navigate(AbortDestinations.AbortedReturnToDesktopWeb)
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
