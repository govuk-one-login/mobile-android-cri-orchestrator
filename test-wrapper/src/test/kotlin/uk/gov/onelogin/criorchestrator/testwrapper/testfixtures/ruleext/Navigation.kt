package uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE_TO_PROVE_YOUR_IDENTITY
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.START

internal fun ComposeTestRule.continueToSelectDocument() {
    onNodeWithText(START)
        .performClick()

    onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
        .assertIsDisplayed()

    onNodeWithText(CONTINUE)
        .performClick()
}
