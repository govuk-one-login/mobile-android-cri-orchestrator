package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModel
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeScreenId

internal class ContinueToProveYourIdentityViewModel(
    private val analytics: ResumeAnalytics,
    private val nfcChecker: NfcChecker,
) : ViewModel(),
    LogTagProvider {
    fun onContinueClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.continue_to_prove_your_identity_screen_button,
        )

        if (nfcChecker.hasNfc()) {
            navigateToPassportJourney()
        } else {
            navigateToDlJourney()
        }
    }

    fun navigateToPassportJourney() {
    }

    fun navigateToDlJourney() {
    }

    fun onScreenStart() {
        analytics.trackScreen(
            id = ResumeScreenId.ContinueToProveYourIdentity,
            title = R.string.continue_to_prove_your_identity_screen_title,
        )
    }
}
