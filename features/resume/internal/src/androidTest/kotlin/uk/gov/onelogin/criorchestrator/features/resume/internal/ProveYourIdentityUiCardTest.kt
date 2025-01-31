package uk.gov.onelogin.criorchestrator.features.resume.internal

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProveYourIdentityUiCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var title: SemanticsMatcher
    private lateinit var body: SemanticsMatcher
    private lateinit var primaryButton: SemanticsMatcher

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        title = hasText(context.getString(R.string.start_id_check_title))
        body = hasText(context.getString(R.string.start_id_check_content))
        primaryButton = hasText(context.getString(R.string.start_id_check_primary_button))
    }

    @Test
    fun verifyUI() {
        composeTestRule.setContent {
            ProveYourIdentityUiCard(onStartClick = {})
        }
        composeTestRule.onNode(title).assertIsDisplayed()
        composeTestRule.onNode(body).assertIsDisplayed()
        composeTestRule.onNode(primaryButton).assertIsDisplayed()
    }

    @Test
    fun onClick() {
        var didClick = false

        composeTestRule.setContent {
            ProveYourIdentityUiCard(onStartClick = {
                didClick = true
            })
        }

        composeTestRule.onNode(primaryButton).performClick()

        assert(didClick)
    }

    @Test
    fun previewTest() {
        composeTestRule.setContent {
            ProveYourIdentityUiCardPreview()
        }
    }
}
