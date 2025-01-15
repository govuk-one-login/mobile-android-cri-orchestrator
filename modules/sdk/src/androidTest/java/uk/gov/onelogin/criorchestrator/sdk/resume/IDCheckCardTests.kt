package uk.gov.onelogin.criorchestrator.sdk.resume

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.SemanticsMatcher
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.gov.onelogin.criorchestrator.sdk.CriOrchestrator
import uk.gov.onelogin.criorchestrator.sdk.R

class IDCheckCardTests {
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
            IDCheckCard(
                component = CriOrchestrator()
            )
        }
        composeTestRule.onNode(title).assertIsDisplayed()
        composeTestRule.onNode(body).assertIsDisplayed()
        composeTestRule.onNode(primaryButton).assertIsDisplayed()
    }
}
