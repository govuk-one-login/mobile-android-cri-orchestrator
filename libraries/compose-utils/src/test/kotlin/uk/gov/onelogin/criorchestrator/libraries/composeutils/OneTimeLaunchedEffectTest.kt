package uk.gov.onelogin.criorchestrator.libraries.composeutils

import android.app.Application
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config as RobolectricConfig

@RunWith(AndroidJUnit4::class)
@RobolectricConfig(application = Application::class)
class OneTimeLaunchedEffectTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val stateRestorationTester = StateRestorationTester(composeTestRule)
    private var backButtonPopsDestination: Boolean = false

    companion object {
        const val EFFECT_ROUTE = "effect-screen"
        const val NEXT_ROUTE = "next-screen"
    }

    @Test
    fun `it runs the effect`() {
        var hasRun = false

        stateRestorationTester.setContent {
            OneTimeLaunchedEffect {
                hasRun = true
            }
        }

        assertTrue(hasRun)
    }

    @Test
    fun `when configuration is changed, it doesn't rerun the effect`() {
        var timesRun = 0

        stateRestorationTester.setContent {
            OneTimeLaunchedEffect {
                timesRun++
            }
        }

        repeat(2) {
            stateRestorationTester.emulateSavedInstanceStateRestore()
        }

        assertEquals(1, timesRun)
    }

    @Test
    fun `when composition is disposed and redisplayed, it reruns the effect`() {
        val toggleDisplayText = "Toggle display"
        var timesRun = 0

        stateRestorationTester.setContent {
            var isDisplayed by remember { mutableStateOf(true) }
            if (isDisplayed) {
                OneTimeLaunchedEffect {
                    timesRun++
                }
            }
            Button(
                onClick = { isDisplayed = !isDisplayed },
            ) {
                Text(toggleDisplayText)
            }
        }

        repeat(2) {
            stateRestorationTester.emulateSavedInstanceStateRestore()
        }

        // Remove the effect from the composition, then add it again
        repeat(2) {
            composeTestRule
                .onNodeWithText(toggleDisplayText)
                .performClick()
        }

        repeat(2) {
            stateRestorationTester.emulateSavedInstanceStateRestore()
        }

        assertEquals(2, timesRun)
    }

    @Test
    fun `nav - when popped back to, it doesn't rerun the effect`() {
        backButtonPopsDestination = false
        var timesRun = 0

        stateRestorationTester.setNavigationContent(
            onEffectRun = { timesRun++ },
            backButtonBehaviour = {
                popBackStack()
            },
        )

        // Navigate away, removing from the composition
        composeTestRule
            .onNodeWithText(NEXT_ROUTE)
            .performClick()

        // Navigate back, restoring the composition
        composeTestRule
            .onNodeWithText(EFFECT_ROUTE)
            .performClick()

        composeTestRule.waitForIdle()

        assertEquals(1, timesRun)
    }

    @Test
    fun `nav - when popped then navigated to, it reruns the effect`() {
        backButtonPopsDestination = true
        var timesRun = 0

        stateRestorationTester.setNavigationContent(
            onEffectRun = { timesRun++ },
            backButtonBehaviour = {
                popBackStack(route = EFFECT_ROUTE, inclusive = true)
                navigate(EFFECT_ROUTE)
            },
        )

        // Navigate away, removing from the composition
        composeTestRule
            .onNodeWithText(NEXT_ROUTE)
            .performClick()

        // Navigate back, restoring the composition
        composeTestRule
            .onNodeWithText(EFFECT_ROUTE)
            .performClick()

        composeTestRule.waitForIdle()

        assertEquals(2, timesRun)
    }

    private fun StateRestorationTester.setNavigationContent(
        onEffectRun: () -> Unit,
        backButtonBehaviour: NavController.() -> Unit,
    ) = setContent {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = EFFECT_ROUTE,
        ) {
            composable(EFFECT_ROUTE) {
                OneTimeLaunchedEffect {
                    onEffectRun()
                }
                NavButton(
                    onClick = { navController.navigate(NEXT_ROUTE) },
                    text = NEXT_ROUTE,
                )
            }
            composable(NEXT_ROUTE) {
                NavButton(
                    onClick = { navController.backButtonBehaviour() },
                    text = EFFECT_ROUTE,
                )
            }
        }
    }
}

@Composable
private fun NavButton(
    onClick: () -> Unit,
    text: String,
) = Button(
    onClick = { onClick() },
) {
    Text(text)
}
