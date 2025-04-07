package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.stubBooleanConfigEntry
import uk.gov.onelogin.criorchestrator.features.config.publicapi.stubStringConfigEntry

@RunWith(AndroidJUnit4::class)
class ConfigEntryTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val onEntryChange = mock<(Config.Entry<Config.Value>) -> Unit>()

    @Test
    fun `when change string entry, it emits to callback`() {
        val initialText = "123"
        val insertedText = "abc"
        val expectedText = "abc123"
        composeTestRule
            .setContent {
                ConfigEntry(
                    entry =
                        stubStringConfigEntry(
                            value = initialText,
                        ),
                    onEntryChange = onEntryChange,
                )
            }

        composeTestRule
            .onNodeWithText(text = initialText)
            .performTextInput(insertedText)

        verify(onEntryChange).invoke(
            stubStringConfigEntry(
                value = expectedText,
            ),
        )
    }

    @Test
    fun `when change boolean entry, it emits to callback`() {
        composeTestRule
            .setContent {
                ConfigEntry(
                    entry =
                        stubBooleanConfigEntry(
                            value = false,
                        ),
                    onEntryChange = onEntryChange,
                )
            }

        composeTestRule
            .onNodeWithText(stubBooleanConfigEntry().key.name)
            .performClick()

        verify(onEntryChange).invoke(
            stubBooleanConfigEntry(value = true),
        )
    }
}
