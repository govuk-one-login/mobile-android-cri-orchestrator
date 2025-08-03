package uk.gov.onelogin.criorchestrator.features.resume.internal.entrypoints

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.ProveYourIdentityEntryPointsImpl
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityViewModel
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.createTestInstance
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModelModule

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityEntryPointsImplTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeProveYourIdentityViewModel =
        ProveYourIdentityViewModel.Companion.createTestInstance()

    private val fakeViewModelProviderFactory =
        viewModelFactory {
            initializer {
                fakeProveYourIdentityViewModel
            }
        }

    private val entryPoints =
        ProveYourIdentityEntryPointsImpl(
            viewModelProviderFactory = fakeViewModelProviderFactory,
            navGraphProviders =
                persistentSetOf(
                    ContinueToProveYourIdentityNavGraphProvider(
                        ContinueToProveYourIdentityViewModelModule.provideFactory(
                            analytics = mock(),
                            nfcChecker = mock(),
                        ),
                    ),
                ),
        )

    @Test
    fun `ProveYourIdentityUiCard implementation displayed`() {
        composeTestRule.setContent {
            GdsTheme {
                entryPoints.ProveYourIdentityCard(
                    modifier = Modifier.Companion,
                )
            }
        }
        composeTestRule.onNodeWithTag(ProveYourIdentityEntryPointsImpl.Companion.TEST_TAG).assertIsDisplayed()
    }
}
