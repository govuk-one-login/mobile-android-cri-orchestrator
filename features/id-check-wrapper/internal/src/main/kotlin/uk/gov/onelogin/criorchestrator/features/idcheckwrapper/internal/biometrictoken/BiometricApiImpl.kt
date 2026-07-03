package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import dev.zacsweers.metro.Inject
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.service.NetworkService
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiRequest.BiometricRequest
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.android.network.api.v2.ApiResponse as ApiResponseV2

private const val BIOMETRIC_TOKEN_ENDPOINT = "/async/biometricToken"

@Inject
class BiometricApiImpl(
    private val networkService: NetworkService,
    private val configStore: ConfigStore,
) : BiometricApi,
    LogTagProvider {
    override suspend fun getBiometricToken(
        sessionId: String,
        documentVariety: DocumentVariety,
    ): ApiResponse {
        val request =
            ApiRequest.Post(
                url = "${configStore.readSingle(IdCheckAsyncBackendBaseUrl).value}$BIOMETRIC_TOKEN_ENDPOINT",
                body =
                    BiometricRequest(
                        sessionId,
                        documentVariety.toDocumentType().backendRepresentation,
                    ),
                headers = listOf("content-type" to "application/json"),
            )

        return when (val response = networkService.makeRequest(request)) {
            is ApiResponseV2.Success -> ApiResponse.Success(response.response)
            is ApiResponseV2.Failure -> ApiResponse.Failure(
                status = response.status ?: 0,
                error = response.error,
            )
        }
    }
}
