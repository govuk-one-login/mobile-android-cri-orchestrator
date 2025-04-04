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
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.features.session.internalapi.StubSessionReader

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityRootTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val viewModel =
        spy(
            ProveYourIdentityViewModel(
                analytics = mock<ResumeAnalytics>(),
                sessionReader = StubSessionReader(),
                logger = SystemLogger(),
            ),
        )

    private val card: SemanticsMatcher = hasTestTag(ProveYourIdentityRootTestTags.CARD)
    private val modal: SemanticsMatcher = hasTestTag(ProveYourIdentityRootTestTags.MODAL)

    // FIXME This content description should be "Cancel button"
    //  https://govukverify.atlassian.net/browse/DCMAW-12003
    private val closeButton = hasContentDescription("Close Button")
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
                persistentSetOf(
                    ContinueToProveYourIdentityNavGraphProvider(
                        ContinueToProveYourIdentityViewModelModule.provideFactory(
                            analytics = mock(),
                            nfcChecker = mock(),
                            configStore = mock(),
                        ),
                    ),
                ),
            )
        }
}
