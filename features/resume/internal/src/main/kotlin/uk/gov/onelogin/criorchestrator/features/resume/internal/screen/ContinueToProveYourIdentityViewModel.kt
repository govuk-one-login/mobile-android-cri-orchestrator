package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeScreenId
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey

internal class ContinueToProveYourIdentityViewModel(
    private val analytics: ResumeAnalytics,
    private val nfcChecker: NfcChecker,
) : ViewModel(),
    LogTagProvider {
    private val _actions = MutableSharedFlow<ContinueToProveYourIdentityAction>()
    val actions: Flow<ContinueToProveYourIdentityAction> = _actions

    fun onContinueClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.continue_to_prove_your_identity_screen_button,
        )

        viewModelScope.launch {
            _actions.emit(
                if (nfcChecker.hasNfc()) {
                    ContinueToProveYourIdentityAction.NavigateToPassport
                } else {
                    ContinueToProveYourIdentityAction.NavigateToDrivingLicense
                },
            )
        }
    }

    fun onScreenStart() {
        analytics.trackScreen(
            id = ResumeScreenId.ContinueToProveYourIdentity,
            title = R.string.continue_to_prove_your_identity_screen_title,
        )
    }

    sealed class ContinueToProveYourIdentityAction {
        data object NavigateToPassport : ContinueToProveYourIdentityAction()

        data object NavigateToDrivingLicense : ContinueToProveYourIdentityAction()
    }
}
