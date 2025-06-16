package uk.gov.onelogin.criorchestrator.libraries.composeutils

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import uk.gov.onelogin.criorchestrator.libraries.composeutils.DropUnlessResumedUnlessDisabledKtTest.Companion.BUTTON

@RunWith(AndroidJUnit4::class)
class DropUnlessResumedUnlessDisabledKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val lifecycleOwner =
        object : LifecycleOwner {
            val registry = LifecycleRegistry(this)
            override val lifecycle: Lifecycle = registry
        }

    private val onClick = mock<() -> Unit>()

    internal companion object {
        const val BUTTON = "Button"
    }

    @Test
    fun `given it isn't disabled, and lifecycle owner is resumed, it can be clicked`() {
        composeTestRule.setContent {
            TestContent(lifecycleOwner, onClick)
        }

        lifecycleOwner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        composeTestRule.clickButton()

        verify(onClick).invoke()
    }

    @Test
    fun `given it isn't disabled, and lifecycle owner isn't resumed, it can't be clicked`() {
        composeTestRule.setContent {
            TestContent(lifecycleOwner, onClick)
        }

        lifecycleOwner.registry.handleLifecycleEvent(Lifecycle.Event.ON_START)

        composeTestRule.clickButton()

        verifyNoInteractions(onClick)
    }

    // Same behaviour as when it's enabled
    @Test
    fun `given it's disabled, and lifecycle owner is resumed, it can be clicked`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDropUnlessResumedDisabled provides true) {
                TestContent(lifecycleOwner, onClick)
            }
        }

        lifecycleOwner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        composeTestRule.clickButton()

        verify(onClick).invoke()
    }

    // When configured for tests, the button can be clicked even when the lifecycle isn't resumed
    @Test
    fun `given it's disabled, and lifecycle owner isn't resumed, it can be clicked`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDropUnlessResumedDisabled provides true) {
                TestContent(lifecycleOwner, onClick)
            }
        }

        lifecycleOwner.registry.handleLifecycleEvent(Lifecycle.Event.ON_START)

        composeTestRule.clickButton()

        verify(onClick).invoke()
    }

    private fun ComposeTestRule.clickButton() =
        composeTestRule
            .onNodeWithText(BUTTON)
            .performClick()
}

@Composable
private fun TestContent(
    lifecycleOwner: LifecycleOwner,
    onClick: () -> Unit,
) = CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner) {
    Button(
        onClick = dropUnlessResumedUnlessDisabledForTesting { onClick() },
    ) {
        Text(BUTTON)
    }
}
