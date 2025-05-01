package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

fun interface BiometricTokenReader {
    suspend fun getBiometricToken(
        sessionId: String,
        documentVariety: DocumentVariety,
    ): BiometricTokenResult
}
