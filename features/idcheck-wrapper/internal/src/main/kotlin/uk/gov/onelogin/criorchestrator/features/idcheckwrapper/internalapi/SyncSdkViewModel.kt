package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

class SyncSdkViewModel(
    sessionStore: SessionStore,
) : ViewModel() {
    internal lateinit var biometricToken: BiometricToken
    internal lateinit var session: Session
    internal lateinit var journeyType: JourneyType

    init {
        viewModelScope.launch {
            session = sessionStore.read().value!!
            biometricToken = stubCallToBiometricToken()
            journeyType = getJourneyTypeFromRedirectUri(session)
        }
    }

    private suspend fun stubCallToBiometricToken(): BiometricToken =
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
