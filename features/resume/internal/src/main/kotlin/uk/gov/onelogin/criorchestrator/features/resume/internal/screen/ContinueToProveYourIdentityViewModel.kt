package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeScreenId
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey

internal class ContinueToProveYourIdentityViewModel(
    private val analytics: ResumeAnalytics,
    private val nfcChecker: NfcChecker,
    private val configStore: ConfigStore,
) : ViewModel(),
    LogTagProvider {
    private val _state = MutableStateFlow<ProveYourIdentityState>(ProveYourIdentityState.Idle)
    val state: StateFlow<ProveYourIdentityState> = _state

    fun onContinueClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.continue_to_prove_your_identity_screen_button,
        )

        runBlocking {
            checkNfc()
        }
    }

    private suspend fun checkNfc() {
        if (configStore.read(NfcConfigKey.StubNcfCheck).first().value) {
            if (configStore.read(NfcConfigKey.IsNfcAvailable).first().value) {
                _state.value = ProveYourIdentityState.NfcAvailable
            } else {
                _state.value = ProveYourIdentityState.NfcNotAvailable
            }
        } else {
            if (nfcChecker.hasNfc()) {
                _state.value = ProveYourIdentityState.NfcAvailable
            } else {
                _state.value = ProveYourIdentityState.NfcNotAvailable
            }
        }
    }

    fun onScreenStart() {
        analytics.trackScreen(
            id = ResumeScreenId.ContinueToProveYourIdentity,
            title = R.string.continue_to_prove_your_identity_screen_title,
        )
    }
}

sealed class ProveYourIdentityState {
    data object Idle : ProveYourIdentityState()

    data object NfcAvailable : ProveYourIdentityState()

    data object NfcNotAvailable : ProveYourIdentityState()
}
