package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.backend.BackendMode
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

fun LauncherData.Companion.createTestInstance(
    session: Session = Session.createTestInstance(),
    biometricToken: BiometricToken = BiometricToken.createTestToken(),
    documentType: DocumentType = DocumentType.NFC_PASSPORT,
) = LauncherData(
    documentType = documentType,
    session = session,
    biometricToken = biometricToken,
    backendMode = BackendMode.V2,
)
