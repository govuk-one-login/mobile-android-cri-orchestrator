@file:OptIn(ExperimentalCoroutinesApi::class)

package uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CHECK_CAN_PROVE_IDENTITY_ANOTHER_WAY
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM_CONTINUE_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM_NO_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE_TO_GOVUK
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE_TO_PROVE_YOUR_IDENTITY
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.DO_YOU_HAVE_A_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.LOADING
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.NO
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.RETURN_TO_GOVUK_MOBILE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.RETURN_TO_GOVUK_ON_COMPUTER
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.SELECT_BIOMETRIC_TOKEN_RESULT
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.START
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.SUCCESS
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.YES
import kotlin.time.Duration

fun ComposeTestRule.continueToSelectDocument() {
    onNodeWithText(START)
        .performClick()

    onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
        .assertIsDisplayed()

    onNodeWithText(CONTINUE)
        .performClick()
}

fun ComposeTestRule.confirmDoYouHaveADrivingLicence(hasDrivingLicence: Boolean) {
    onNode(isHeading() and hasText(DO_YOU_HAVE_A_DRIVING_LICENCE))
        .assertIsDisplayed()

    val selection =
        when (hasDrivingLicence) {
            true -> YES
            false -> NO
        }
    onNodeWithText(selection, useUnmergedTree = true)
        .performScrollTo()
        .performClick()

    onNodeWithText(CONTINUE)
        .performClick()
}

fun ComposeTestRule.confirmContinueWithDrivingLicence() {
    onNode(isHeading() and hasText(CONFIRM_CONTINUE_DRIVING_LICENCE))
        .assertIsDisplayed()

    onNodeWithText(CONFIRM)
        .performClick()
}

fun ComposeTestRule.confirmNoDrivingLicence() {
    onNodeWithText(CONFIRM_NO_DRIVING_LICENCE)
        .assertIsDisplayed()

    onNodeWithText(CONFIRM)
        .performClick()
}

fun ComposeTestRule.selectBiometricTokenResult() {
    onNode(hasText(SELECT_BIOMETRIC_TOKEN_RESULT))
        .assertIsDisplayed()

    onNodeWithText(SUCCESS, useUnmergedTree = true)
        .performClick()
}

fun ComposeTestRule.seeLoading(
    testScope: TestScope,
    duration: Duration,
) {
    onNodeWithText(LOADING)
        .assertIsDisplayed()

    testScope.advanceTimeBy(duration)
}

fun ComposeTestRule.continueToGovUkWebsite() {
    onNode(hasText(RETURN_TO_GOVUK_MOBILE))
        .assertIsDisplayed()

    onNodeWithText(CONTINUE_TO_GOVUK, useUnmergedTree = true, substring = true)
        .performClick()
}

fun ComposeTestRule.continueToCheckIfCanProveIdentityAnotherWay() {
    onNodeWithText(CHECK_CAN_PROVE_IDENTITY_ANOTHER_WAY)
        .assertIsDisplayed()

    onNodeWithText(CONTINUE)
        .performClick()
}

fun ComposeTestRule.seeReturnToGovUkOnComputer() {
    onNodeWithText(RETURN_TO_GOVUK_ON_COMPUTER)
        .assertIsDisplayed()
}
