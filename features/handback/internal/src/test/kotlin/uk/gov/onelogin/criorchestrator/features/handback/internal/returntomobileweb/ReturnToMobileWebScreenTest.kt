package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

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
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class ReturnToMobileWebScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val context: Context = ApplicationProvider.getApplicationContext()

    private val viewModel =
        ReturnToMobileWebViewModel(
            analytics = mock(),
        )

    private val webNavigator = FakeWebNavigator()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ReturnToMobileWebScreen(
                viewModel = viewModel,
                webNavigator = webNavigator,
                redirectUri = REDIRECT_URI,
            )
        }
    }

    @Test
    fun `when continue to gov uk website button is clicked, it opens the session redirect uri`() {
        composeTestRule
            .onNode(
                hasTextStartingWith(context.getString(ReturnToMobileWebConstants.buttonId)),
            ).performClick()

        assertEquals(REDIRECT_URI, webNavigator.openUrl)
    }

    @Test
    fun `when talkback is enabled, it reads out Gov dot UK correctly`() {
        val title =
            composeTestRule
                .onNode(hasText(context.getString(ReturnToMobileWebConstants.titleId)))
        title.assertContentDescriptionContains("Gov dot UK", true)

        val body =
            composeTestRule
                .onNode(hasText(context.getString(R.string.handback_returntomobileweb_body2)))
        body.assertContentDescriptionContains("Gov dot UK", true)
    }

    @Test
    fun `when talkback is enabled, it reads out external site button correctly`() {
        composeTestRule
            .onNode(
                hasText(
                    context.getString(R.string.handback_returntomobileweb_button),
                    true,
                ),
            ).assertContentDescriptionContains(". opens in web browser")
    }
}
