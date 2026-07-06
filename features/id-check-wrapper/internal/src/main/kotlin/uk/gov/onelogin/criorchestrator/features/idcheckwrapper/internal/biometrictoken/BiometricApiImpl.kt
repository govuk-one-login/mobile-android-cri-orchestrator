package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import dev.zacsweers.metro.Inject
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkService
import uk.gov.android.network.service.NetworkingException
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiRequest.BiometricRequest
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

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
    ): ApiResponse<String, NetworkingException> {
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

        return networkService.makeRequest(request)
    }
}
