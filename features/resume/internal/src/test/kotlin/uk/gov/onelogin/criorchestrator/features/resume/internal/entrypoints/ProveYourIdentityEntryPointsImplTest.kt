package uk.gov.onelogin.criorchestrator.features.resume.internal.entrypoints

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.ViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.zacsweers.metro.Provider
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
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
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModel
import kotlin.reflect.KClass

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityEntryPointsImplTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeProveYourIdentityViewModel =
        ProveYourIdentityViewModel.createTestInstance()

    private val fakeViewModelFactoryProvider =
        Provider<ViewModel> {
            fakeProveYourIdentityViewModel
        }

    private val entryPoints =
        ProveYourIdentityEntryPointsImpl(
            metroVmf =
                object : MetroViewModelFactory() {
                    override val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>> =
                        mapOf(
                            ProveYourIdentityViewModel::class to fakeViewModelFactoryProvider,
                            ContinueToProveYourIdentityViewModel::class to
                                Provider {
                                    ContinueToProveYourIdentityViewModel(
                                        analytics = mock(),
                                        nfcChecker = mock(),
                                    )
                                },
                        )
                },
            navGraphProviders =
                persistentSetOf(
                    ContinueToProveYourIdentityNavGraphProvider(),
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
