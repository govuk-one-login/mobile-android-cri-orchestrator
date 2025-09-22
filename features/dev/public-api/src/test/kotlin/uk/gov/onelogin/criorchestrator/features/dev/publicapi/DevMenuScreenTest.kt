package uk.gov.onelogin.criorchestrator.features.dev.publicapi

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@RunWith(AndroidJUnit4::class)
class DevMenuScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private companion object {
        const val CONTENT = "Content"
        private val testGraph = TestGraph(CONTENT)
    }

    @Test
    fun `it displays the entry point`() {
        composeTestRule.setContent {
            DevMenuScreen(graph = testGraph)
        }

        composeTestRule
            .onNodeWithText(CONTENT)
            .assertIsDisplayed()
    }

    @Test
    fun `it displays the entry point - legacy`() {
        composeTestRule.setContent {
            DevMenuScreen(component = testGraph)
        }

        composeTestRule
            .onNodeWithText(CONTENT)
            .assertIsDisplayed()
    }
}

class TestGraph(
    private val content: String,
) : DevMenuEntryPointsProviders,
    CriOrchestratorGraph {
    override fun devMenuEntryPoints(): DevMenuEntryPoints =
        DevMenuEntryPoints {
            Text(content)
        }
}
