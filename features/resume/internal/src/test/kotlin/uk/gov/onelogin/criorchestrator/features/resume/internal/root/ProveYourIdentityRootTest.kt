package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.AndroidResourceProvider

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityRootTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val resourceProvider = AndroidResourceProvider(context)
    private val viewModel =
        spy(
            ProveYourIdentityViewModel(
                analyticsLogger = FakeAnalyticsLogger(),
                resourceProvider = resourceProvider,
            ),
        )

    private val card: SemanticsMatcher = hasTestTag(ProveYourIdentityRootTestTags.CARD)
    private val modal: SemanticsMatcher = hasTestTag(ProveYourIdentityRootTestTags.MODAL)
    private val closeButton = hasContentDescription("Close")
    private val continueButton = hasText(context.getString(R.string.start_id_check_primary_button))

    @Test
    fun `it displays the card`() =
        runTest {
            composeTestRule.displayProveYourIdentityRoot()

            composeTestRule
                .onNode(card)
                .assertIsDisplayed()
        }

    @Test
    fun `it launches the modal`() =
        runTest {
            composeTestRule.displayProveYourIdentityRoot()

            composeTestRule
                .onNode(modal)
                .assertIsDisplayed()
        }

    @Test
    fun `when modal is dismissed, it hides the modal`() {
        composeTestRule.displayProveYourIdentityRoot()

        composeTestRule
            .onNode(closeButton)
            .performClick()

        composeTestRule
            .onNode(modal)
            .assertIsNotDisplayed()
    }

    @Test
    fun `given the modal has been dismissed, when continue is clicked, it shows the modal again`() {
        composeTestRule.displayProveYourIdentityRoot()

        composeTestRule
            .onNode(closeButton)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        composeTestRule
            .onNode(modal)
            .assertIsDisplayed()

        verify(viewModel).start()
    }

    private fun ComposeContentTestRule.displayProveYourIdentityRoot() =
        setContent {
            ProveYourIdentityRoot(
                viewModel,
            )
        }
}
