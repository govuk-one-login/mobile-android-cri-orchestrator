package uk.gov.onelogin.criorchestrator.features.handback.internal.disablebackbutton

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.libraries.navigation.TestNavHost

@RunWith(AndroidJUnit4::class)
abstract class DisableBackButtonTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val originText = "Go to second screen"

    fun setContent(composable: @Composable () -> Unit) {
        composeTestRule.setContent {
            TestNavHost(originText) {
                composable()
            }
        }
    }

    @Test
    fun `when the back button is pressed, the user remains on this screen`() {
        composeTestRule
            .onNode(hasText(originText))
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNode(hasText(originText))
            .assertIsNotDisplayed()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule
            .onNode(hasText(originText))
            .assertIsNotDisplayed()
    }
}