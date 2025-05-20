package uk.gov.onelogin.criorchestrator.libraries.composeutils

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.espresso.Espresso.pressBack

fun ComposeTestRule.goBack(times: Int = 1) =
    repeat(times) {
        pressBack()
        waitForIdle()
    }
