package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

fun interface BiometricTokenReader {
    suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): BiometricTokenResult
}
