package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

interface BiometricTokenReader {
    suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): BiometricTokenResult
}
