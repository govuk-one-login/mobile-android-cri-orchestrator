package uk.gov.onelogin.criorchestrator.libraries.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.libraries.navigation.fixtures.FakeNavGraph

@RunWith(AndroidJUnit4::class)
class CompositeNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `navigate between destinations`() {
        composeTestRule.setContent {
            CompositeNavHost(
                navGraphProviders =
                    persistentSetOf(
                        FakeNavGraph.Provider(),
                    ),
                startDestination = FakeNavGraph.Destination.First,
            )
        }

        composeTestRule.assertFirstScreenDisplayed()

        composeTestRule
            .onNodeWithText(FakeNavGraph.Buttons.FIRST)
            .performClick()

        composeTestRule.assertSecondScreenIsDisplayed()
    }

    private fun ComposeContentTestRule.assertFirstScreenDisplayed() {
        onNodeWithText(FakeNavGraph.Buttons.FIRST)
            .assertIsDisplayed()

        onNodeWithText(FakeNavGraph.Buttons.SECOND)
            .assertIsNotDisplayed()
    }

    private fun ComposeContentTestRule.assertSecondScreenIsDisplayed() {
        onNodeWithText(FakeNavGraph.Buttons.FIRST)
            .assertIsNotDisplayed()

        onNodeWithText(FakeNavGraph.Buttons.SECOND)
            .assertIsDisplayed()
    }
}
