package uk.gov.onelogin.criorchestrator.features.resume.internal.card

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.features.resume.internal.R

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityUiCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var primaryButton: SemanticsMatcher

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        primaryButton = hasText(context.getString(R.string.start_id_check_primary_button))
    }

    @Test
    fun `when start button is clicked, it calls onStartClick`() {
        var didClick = false

        composeTestRule.setContent {
            ProveYourIdentityUiCard(onStartClick = {
                didClick = true
            })
        }

        composeTestRule.onNode(primaryButton).performClick()

        assert(didClick)
    }
}
