package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

class StubBiometricTokenReader(
    private val biometricTokenResult: BiometricTokenResult,
) : BiometricTokenReader {
    override suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): BiometricTokenResult = biometricTokenResult
}
