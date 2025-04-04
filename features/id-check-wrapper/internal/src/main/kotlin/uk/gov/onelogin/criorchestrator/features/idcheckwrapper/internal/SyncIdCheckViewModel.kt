package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

import androidx.lifecycle.ViewModel
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

class SyncIdCheckViewModel(
    sessionStore: SessionStore,
) : ViewModel() {
    // DCMAW-12468: Viewmodel shouldn't have such detailed knowledge about how we collate the
    // information needed to start the ID Check SDK.
    internal var biometricToken: BiometricToken = stubCallToBiometricTokenEndpoint()
    internal var session: Session = sessionStore.read().value!!
    internal var journeyType: JourneyType = getJourneyTypeFromRedirectUri(session)

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
