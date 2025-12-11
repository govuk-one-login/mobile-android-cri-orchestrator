package uk.gov.onelogin.criorchestrator.features.dev.internal.entrypoints

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.ViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.zacsweers.metro.Provider
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.dev.internal.DevMenuEntryPointsImpl
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuViewModel
import kotlin.reflect.KClass

@RunWith(AndroidJUnit4::class)
class DevMenuEntryPointsImplTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel =
        DevMenuViewModel(
            configStore = FakeConfigStore(),
        )

    val entryPoints =
        DevMenuEntryPointsImpl(
            metroVmf =
                object : MetroViewModelFactory() {
                    override val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>> =
                        mapOf(DevMenuViewModel::class to Provider { viewModel })
                },
        )

    @Test
    fun `DevMenuScreen is displayed`() {
        composeTestRule.setContent {
            GdsTheme {
                entryPoints.DevMenuScreen(
                    modifier = Modifier,
                )
            }
        }
        composeTestRule.onNodeWithTag(DevMenuEntryPointsImpl.TEST_TAG).assertIsDisplayed()
    }
}
