package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import dev.zacsweers.metro.Inject
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.ContentType
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiRequest.BiometricRequest
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

private const val BIOMETRIC_TOKEN_ENDPOINT = "/async/biometricToken"

@Inject
class BiometricApiImpl(
    private val httpClient: GenericHttpClient,
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
                contentType = ContentType.APPLICATION_JSON,
            )

        return httpClient.makeRequest(
            apiRequest = request,
        )
    }
}
