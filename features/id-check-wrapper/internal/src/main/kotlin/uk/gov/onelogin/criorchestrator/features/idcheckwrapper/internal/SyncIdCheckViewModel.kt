package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

class SyncIdCheckViewModel(
    private val sessionStore: SessionStore,
) : ViewModel(),
    LogTagProvider {
    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
    internal var biometricToken: BiometricToken = stubCallToBiometricTokenEndpoint()
    private val _session: MutableStateFlow<Session?> = MutableStateFlow(null)
    val session: StateFlow<Session?> = _session.asStateFlow()
    private val _journeyType: MutableStateFlow<JourneyType?> = MutableStateFlow(null)
    val journeyType: StateFlow<JourneyType?> = _journeyType.asStateFlow()

    fun start() {
        viewModelScope.launch {
            sessionStore.read().collect { session ->
                _session.emit(session)
                if (session != null) {
                    _journeyType.emit(getJourneyTypeFromRedirectUri(session))
                }
            }
        }
    }

    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
    private fun stubCallToBiometricTokenEndpoint(): BiometricToken =
        BiometricToken(
            accessToken = "Stub Access Token",
            opaqueId = "Stub Opaque ID",
        )

    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
    private fun getJourneyTypeFromRedirectUri(session: Session): JourneyType =
        if (session.redirectUri.isNullOrBlank()) {
            JourneyType.DESKTOP_APP_DESKTOP
        } else {
            JourneyType.MOBILE_APP_MOBILE
        }
}
