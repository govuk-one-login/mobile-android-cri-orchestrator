package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R

@RunWith(AndroidJUnit4::class)
class ReturnToDesktopWebScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel =
        ReturnToDesktopWebViewModel(
            analytics = mock(),
        )

    private val reviewRequester = FakeReviewRequester()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ReturnToDesktopWebScreen(
                viewModel = viewModel,
                reviewRequester = reviewRequester,
            )
        }
    }

    @Test
    fun `when I view the return to desktop screen, I am asked to review the app`() {
        assertTrue(reviewRequester.requestedReview)
    }

    @Test
    fun `when talkback is enabled, it reads out Gov dot UK correctly`() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val title =
            composeTestRule
                .onNode(hasText(context.getString(ReturnToDesktopWebConstants.titleId)))
        title.assertContentDescriptionContains("Gov dot UK", true)

        val body =
            composeTestRule
                .onNode(hasText(context.getString(R.string.handback_returntodesktopweb_body2)))
        body.assertContentDescriptionContains("Gov dot UK", true)
    }
}
