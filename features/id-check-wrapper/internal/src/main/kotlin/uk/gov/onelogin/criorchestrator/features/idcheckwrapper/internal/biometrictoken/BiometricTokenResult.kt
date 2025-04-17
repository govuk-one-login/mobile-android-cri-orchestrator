package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.idcheck.repositories.api.vendor.BiometricToken

sealed interface BiometricTokenResult {
    data class Success(
        val token: BiometricToken,
    ) : BiometricTokenResult

    object Offline : BiometricTokenResult

    data class Error(
        val error: Exception,
        val statusCode: Int? = null,
    ) : BiometricTokenResult
}
