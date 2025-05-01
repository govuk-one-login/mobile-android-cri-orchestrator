package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.delay
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

class StubBiometricTokenReader(
    private val biometricTokenResult: BiometricTokenResult,
    private val delay: Long = 0,
) : BiometricTokenReader {
    override suspend fun getBiometricToken(
        sessionId: String,
        documentVariety: DocumentVariety,
    ): BiometricTokenResult {
        // Used to simulate network delay
        delay(delay)
        return biometricTokenResult
    }
}
