package uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CHECK_CAN_PROVE_IDENTITY_ANOTHER_WAY
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM_NO_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE_TO_PROVE_YOUR_IDENTITY
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.DO_YOU_HAVE_A_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.LOADING
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.NO
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.RETURN_TO_GOVUK_ON_COMPUTER
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.START

internal fun ComposeTestRule.continueToSelectDocument() {
    onNodeWithText(START)
        .performClick()

    onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
        .assertIsDisplayed()

    onNodeWithText(CONTINUE)
        .performClick()
}

@OptIn(ExperimentalTestApi::class)
internal fun ComposeTestRule.continueToAbortedDesktop() {
    onNodeWithText(START)
        .performClick()

    onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
        .assertIsDisplayed()

    onNodeWithText(CONTINUE)
        .performClick()

    onNode(isHeading() and hasText(DO_YOU_HAVE_A_DRIVING_LICENCE))
        .assertIsDisplayed()

    onNodeWithText(NO, useUnmergedTree = true)
        .performScrollTo()
        .performClick()

    onNodeWithText(CONTINUE)
        .performClick()

    onNodeWithText(CONFIRM_NO_DRIVING_LICENCE)
        .assertIsDisplayed()

    onNodeWithText(CONFIRM)
        .performClick()

    onNodeWithText(CHECK_CAN_PROVE_IDENTITY_ANOTHER_WAY)
        .assertIsDisplayed()

    onNodeWithText(CONTINUE)
        .performClick()

    waitUntilDoesNotExist(
        hasText(LOADING),
        timeoutMillis = 5000L,
    )
    onNodeWithText(RETURN_TO_GOVUK_ON_COMPUTER)
        .assertIsDisplayed()
}
