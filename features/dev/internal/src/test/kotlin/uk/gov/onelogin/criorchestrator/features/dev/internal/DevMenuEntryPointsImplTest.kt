package uk.gov.onelogin.criorchestrator.features.dev.internal

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.internal.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuViewModel

@RunWith(AndroidJUnit4::class)
class DevMenuEntryPointsImplTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val entryPoints =
        DevMenuEntryPointsImpl(
            viewModelProviderFactory =
                viewModelFactory {
                    initializer {
                        DevMenuViewModel(
                            configStore = FakeConfigStore(),
                        )
                    }
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
