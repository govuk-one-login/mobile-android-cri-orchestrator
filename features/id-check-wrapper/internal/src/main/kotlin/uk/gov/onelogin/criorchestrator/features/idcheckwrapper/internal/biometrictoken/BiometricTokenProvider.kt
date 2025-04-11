package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.flow.Flow

interface BiometricTokenProvider {
    fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): Flow<BiometricTokenResult>
}
