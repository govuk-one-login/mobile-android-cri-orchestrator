package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortreturntodesktopweb

import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R

@RunWith(AndroidJUnit4::class)
class ConfirmAbortReturnToDesktopWebScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel =
        ConfirmAbortReturnToDesktopWebViewModel(
            analytics = mock(),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ConfirmAbortReturnToDesktopWebScreen(
                viewModel = viewModel,
            )
        }
    }

    @Test
    fun `when talkback is enable, it reads out gov dot uk`() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val title =
            composeTestRule.onNode(
                hasText(
                    context.getString(ConfirmAbortReturnToDesktopWebConstants.titleId),
                ),
            )
        title.assertContentDescriptionContains("Gov dot UK", true)

        val body =
            composeTestRule.onNode(
                hasText(
                    context.getString(R.string.handback_confirmabortreturntodesktopweb_body1),
                ),
            )

        body.assertContentDescriptionContains("Gov dot UK", true)
    }
}
