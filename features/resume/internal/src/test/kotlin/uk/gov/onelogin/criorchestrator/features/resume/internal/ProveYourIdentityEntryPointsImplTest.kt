package uk.gov.onelogin.criorchestrator.features.resume.internal

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
import org.mockito.Mockito.mock
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityViewModel
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.features.session.internal.StubSessionReader

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityEntryPointsImplTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeProveYourIdentityViewModel =
        ProveYourIdentityViewModel(
            analytics = mock<ResumeAnalytics>(),
            sessionReader = StubSessionReader(),
            logger = SystemLogger(),
        )

    val fakeViewModelProviderFactory =
        viewModelFactory {
            initializer {
                fakeProveYourIdentityViewModel
            }
        }

    val entryPoints =
        ProveYourIdentityEntryPointsImpl(
            viewModelProviderFactory = fakeViewModelProviderFactory,
            navGraphProviders =
                persistentSetOf(
                    ContinueToProveYourIdentityNavGraphProvider(
                        ContinueToProveYourIdentityViewModelModule.provideFactory(
                            analytics = mock(),
                        ),
                    ),
                ),
        )

    @Test
    fun `ProveYourIdentityUiCard implementation displayed`() {
        composeTestRule.setContent {
            GdsTheme {
                entryPoints.ProveYourIdentityCard(
                    modifier = Modifier,
                )
            }
        }
        composeTestRule.onNodeWithTag(ProveYourIdentityEntryPointsImpl.TEST_TAG).assertIsDisplayed()
    }
}
