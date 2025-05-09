package uk.gov.onelogin.criorchestrator.features.handback.internal

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

abstract class DisableBackButtonTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    fun setContent(composable: @Composable () -> Unit) {
        composeTestRule.setContent {
            TestNavigationHost {
                composable()
            }
        }
    }

    @Test
    fun `when the back button is pressed, the user remains on this screen`() {
        composeTestRule
            .onNode(hasText("Hello World"))
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNode(hasText("Hello World"))
            .assertIsNotDisplayed()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule
            .onNode(hasText("Hello World"))
            .assertIsNotDisplayed()
    }
}
