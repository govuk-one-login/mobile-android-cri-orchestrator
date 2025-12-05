package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import android.content.Context
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.zacsweers.metro.Provider
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModel
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LocalDropUnlessResumedDisabled
import kotlin.reflect.KClass

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityRootTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val viewModel =
        spy(
            ProveYourIdentityViewModel.createTestInstance(),
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

        verify(viewModel).onStartClick()
    }

    private fun ComposeContentTestRule.displayProveYourIdentityRoot() {
        val navGraphProviders =
            persistentSetOf(
                ContinueToProveYourIdentityNavGraphProvider(),
            )
        val metroVmf =
            object : MetroViewModelFactory() {
                override val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>> =
                    mapOf(
                        ContinueToProveYourIdentityViewModel::class to
                            Provider {
                                ContinueToProveYourIdentityViewModel(
                                    analytics = mock(),
                                    nfcChecker = mock(),
                                )
                            },
                    )
            }
        setContent {
            CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
                CompositionLocalProvider(LocalDropUnlessResumedDisabled provides true) {
                    ProveYourIdentityRoot(
                        viewModel = viewModel,
                        navGraphProviders = navGraphProviders,
                    )
                }
            }
        }
    }
}
