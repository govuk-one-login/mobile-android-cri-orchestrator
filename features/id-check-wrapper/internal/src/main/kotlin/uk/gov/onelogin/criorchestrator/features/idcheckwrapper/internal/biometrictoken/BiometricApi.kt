package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.android.network.service.v2.NetworkServiceResponse
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

fun interface BiometricApi {
    suspend fun getBiometricToken(sessionId: String, documentVariety: DocumentVariety): NetworkServiceResponse
}
