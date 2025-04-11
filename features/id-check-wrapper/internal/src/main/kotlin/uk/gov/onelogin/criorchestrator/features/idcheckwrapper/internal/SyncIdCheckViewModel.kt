package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenProvider
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckAction
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

class SyncIdCheckViewModel(
    private val sessionStore: SessionStore,
    private val biometricTokenProvider: BiometricTokenProvider,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SyncIdCheckAction>()
    val actions: Flow<SyncIdCheckAction> = _actions

    private val _state = MutableStateFlow<SyncIdCheckState>(SyncIdCheckState.Idle)
    val state: StateFlow<SyncIdCheckState> = _state

    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
//    internal var session: Session = sessionStore.read().value!!
//    internal var journeyType: JourneyType = getJourneyTypeFromRedirectUri(session)

    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
    fun getBiometricToken(documentType: String) {
        viewModelScope.launch {
            biometricTokenProvider
                .getBiometricToken("session.sessionId", documentType)
                .collect { result ->
                    when (result) {
                        is BiometricTokenResult.Error ->
                            _actions.emit(
                                SyncIdCheckAction.NavigateToUnRecoverableError,
                            )

                        BiometricTokenResult.Loading ->
                            _state.value = SyncIdCheckState.Loading

                        BiometricTokenResult.Offline -> {
                            SyncIdCheckAction.NavigateToRecoverableError
                        }

                        is BiometricTokenResult.Success -> {}
                    }
                }
        }
    }

    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
    private fun getJourneyTypeFromRedirectUri(): JourneyType {
        val session = sessionStore.read().value

        return if (session?.redirectUri.isNullOrBlank()) {
            JourneyType.DESKTOP_APP_DESKTOP
        } else {
            JourneyType.MOBILE_APP_MOBILE
        }
    }
}
