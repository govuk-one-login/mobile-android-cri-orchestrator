package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile

import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherRule
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class FaceScanLimitReachedMobileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testScope = TestScope()

    val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testScope)

    private val viewModel =
        FaceScanLimitReachedMobileViewModel(
            analytics = mock(),
        )

    private val webNavigator = FakeWebNavigator()

    @Before
    fun setup() {
        composeTestRule.setContent {
            FaceScanLimitReachedMobileScreen(
                viewModel = viewModel,
                webNavigator = webNavigator,
                redirectUri = REDIRECT_URI,
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when continue to gov uk website button is clicked, it opens the session redirect uri`() =
        runTest {
            composeTestRule
                .onNode(
                    hasTextStartingWith(context.getString(FaceScanLimitReachedMobileConstants.buttonId)),
                ).performClick()

            testScope.advanceUntilIdle()
            assertEquals(REDIRECT_URI, webNavigator.openUrl)
        }

    @Test
    fun `when talkback is enabled, it reads out Gov dot UK correctly`() {
        val body =
            composeTestRule
                .onNode(hasText(context.getString(R.string.handback_facescanlimitreachedmobile_body2)))
        body.assertContentDescriptionContains("Gov dot UK", true)
    }

    @Test
    fun `when talkback is enabled, it reads out external site button correctly`() {
        composeTestRule
            .onNode(
                hasText(
                    context.getString(R.string.handback_facescanlimitreachedmobile_button),
                    true,
                ),
            ).assertContentDescriptionContains(". opens in web browser")
    }
}
