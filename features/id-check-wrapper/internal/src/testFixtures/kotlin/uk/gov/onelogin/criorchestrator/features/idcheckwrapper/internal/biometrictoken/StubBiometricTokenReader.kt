package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.delay

class StubBiometricTokenReader(
    private val biometricTokenResult: BiometricTokenResult,
    private val delay: Long = 0,
) : BiometricTokenReader {
    override suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): BiometricTokenResult {
        // Used to simulate network delay
        delay(delay)
        return biometricTokenResult
    }
}
