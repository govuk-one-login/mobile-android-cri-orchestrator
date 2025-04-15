package uk.gov.onelogin.criorchestrator.libraries.composeutils

import android.app.Application
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
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
}
