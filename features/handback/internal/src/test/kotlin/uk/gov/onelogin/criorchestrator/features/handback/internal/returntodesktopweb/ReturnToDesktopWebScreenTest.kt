package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.DebugRequestAppReview
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherRule

@RunWith(AndroidJUnit4::class)
class ReturnToDesktopWebScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testScope = TestScope()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testScope)

    private val requestAppReview =
        DebugRequestAppReview(
            context = ApplicationProvider.getApplicationContext<Context>(),
        )

    private val viewModel =
        ReturnToDesktopWebViewModel(
            analytics = mock(),
            requestAppReview = requestAppReview,
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ReturnToDesktopWebScreen(
                viewModel = viewModel,
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when I view the return to desktop screen, I am asked to review the app`() =
        runTest {
            advanceUntilIdle()

            assertTrue(requestAppReview.hasRequestedReview)
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
