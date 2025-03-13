package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModel
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics

internal class ContinueToProveYourIdentityViewModel(
    private val analytics: ResumeAnalytics,
) : ViewModel(),
    LogTagProvider {
    fun onContinueClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.continue_to_prove_your_identity_screen_button,
        )
    }
}
