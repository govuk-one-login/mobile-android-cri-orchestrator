package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import android.content.Context
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherRule

@RunWith(AndroidJUnit4::class)
class FaceScanLimitReachedDesktopScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testScope = TestScope()

    val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testScope)

    private val viewModel =
        FaceScanLimitReachedDesktopViewModel(
            analytics = mock(),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            FaceScanLimitReachedDesktopScreen(
                viewModel = viewModel,
            )
        }
    }

    @Test
    fun `when talkback is enabled, it reads out Gov dot UK correctly`() {
        val body =
            composeTestRule
                .onNode(hasText(context.getString(R.string.handback_facescanlimitreacheddesktop_body2)))
        body.assertContentDescriptionContains("Gov dot UK", true)
    }
}
