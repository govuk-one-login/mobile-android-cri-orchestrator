package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType

data class LauncherData(
    val sessionId: String,
    val biometricToken: BiometricToken,
    val journeyType: JourneyType,
    val documentType: DocumentType,
)
