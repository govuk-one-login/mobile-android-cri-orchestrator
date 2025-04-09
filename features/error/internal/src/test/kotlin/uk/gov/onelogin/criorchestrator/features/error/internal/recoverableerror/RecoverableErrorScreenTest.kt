package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

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
import uk.gov.onelogin.criorchestrator.features.error.internal.R

@RunWith(AndroidJUnit4::class)
class RecoverableErrorScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val button = hasText(context.getString(R.string.error_recoverableerror_button))

    private val navController: NavController = mock()

    private val viewModel =
        RecoverableErrorViewModel(
            analytics = mock(),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            RecoverableErrorScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when primary button is clicked, it goes back`() {
        composeTestRule
            .onNode(button)
            .performClick()

        verify(navController).popBackStack()
    }
}
