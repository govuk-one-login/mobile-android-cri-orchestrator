package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class EnterTextDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var value: String? = null

    private lateinit var showDialogButton: SemanticsMatcher
    private lateinit var textField: SemanticsMatcher
    private lateinit var cancelEntryButton: SemanticsMatcher
    private lateinit var confirmEntryButton: SemanticsMatcher

    @Before
    fun setUp() {
        showDialogButton = hasText("Start manual journey")
        textField = hasText("Enter sub value: ", true)
        cancelEntryButton = hasText("Cancel")
        confirmEntryButton = hasText("Enter")

        composeTestRule.setContent {
            EnterTextDialog(
                didEnterValue = {
                    value = it
                },
            )
        }
    }

    @Test
    fun `when the component appears, the dialog is hidden`() {
        composeTestRule.onNode(textField).assertIsNotDisplayed()
        composeTestRule.onNode(cancelEntryButton).assertIsNotDisplayed()
        composeTestRule.onNode(confirmEntryButton).assertIsNotDisplayed()
    }

    @Test
    fun `when the button is clicked, the dialog appears`() {
        composeTestRule.onNode(showDialogButton).performClick()

        composeTestRule.onNode(textField).assertIsDisplayed()
        composeTestRule.onNode(cancelEntryButton).assertIsDisplayed()
        composeTestRule.onNode(confirmEntryButton).assertIsDisplayed()
    }

    @Test
    fun `when the confirm button is clicked, the value is set`() {
        val subEntry = "abcdef"
        composeTestRule.onNode(showDialogButton).performClick()

        composeTestRule.onNode(textField).performTextReplacement(subEntry)
        composeTestRule.onNode(confirmEntryButton).performClick()

        assertEquals(value, subEntry)
    }

    @Test
    fun `when the cancel button is clicked, the value is not set`() {
        composeTestRule.onNode(showDialogButton).performClick()

        composeTestRule.onNode(textField).performTextReplacement("abcdef")
        composeTestRule.onNode(cancelEntryButton).performClick()

        assertEquals(value, null)
    }
}
