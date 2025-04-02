package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

class SyncIdCheckViewModel(
    sessionStore: SessionStore,
) : ViewModel() {
    internal lateinit var biometricToken: BiometricToken
    internal lateinit var session: Session
    internal lateinit var journeyType: JourneyType

    init {
        viewModelScope.launch {
            session = sessionStore.read().value!!
            biometricToken = stubCallToBiometricTokenEndpoint()
            journeyType = getJourneyTypeFromRedirectUri(session)
        }
    }

    // DCMAW-12468: Actually call and properly stub the Biometric Token endpoint call
    private suspend fun stubCallToBiometricTokenEndpoint(): BiometricToken =
        BiometricToken(
            accessToken = "Stub Access Token",
            opaqueId = "Stub Opaque ID",
        )

    private fun getJourneyTypeFromRedirectUri(session: Session): JourneyType =
        if (session.redirectUri.isNullOrBlank()) {
            JourneyType.DESKTOP_APP_DESKTOP
        } else {
            JourneyType.MOBILE_APP_MOBILE
        }
}
