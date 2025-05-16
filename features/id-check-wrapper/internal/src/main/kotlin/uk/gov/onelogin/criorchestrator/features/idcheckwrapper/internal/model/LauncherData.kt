package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType as IdCheckJourneyType

data class LauncherData(
    val session: Session,
    val biometricToken: BiometricToken,
    val documentType: DocumentType,
) {
    companion object;

    val sessionId = session.sessionId

    val journeyType: IdCheckJourneyType =
        when (session.journeyType) {
            JourneyType.DesktopAppDesktop -> IdCheckJourneyType.DESKTOP_APP_DESKTOP
            is JourneyType.MobileAppMobile -> IdCheckJourneyType.MOBILE_APP_MOBILE
        }

    val sessionJourneyType: JourneyType = session.journeyType
}
