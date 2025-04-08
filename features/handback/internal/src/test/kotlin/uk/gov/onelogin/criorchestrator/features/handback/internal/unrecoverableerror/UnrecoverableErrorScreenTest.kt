package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import android.content.Context
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
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations

@RunWith(AndroidJUnit4::class)
class UnrecoverableErrorScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val button = hasText(context.getString(R.string.handback_unrecoverableerror_button))

    private val navController: NavController = mock()

    private val viewModel =
        UnrecoverableErrorViewModel(
            analytics = mock(),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            UnrecoverableErrorScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when button is clicked, it navigates to abort flow`() {
        composeTestRule
            .onNode(button)
            .performClick()

        verify(navController).navigate(HandbackDestinations.Abort)
    }
}
