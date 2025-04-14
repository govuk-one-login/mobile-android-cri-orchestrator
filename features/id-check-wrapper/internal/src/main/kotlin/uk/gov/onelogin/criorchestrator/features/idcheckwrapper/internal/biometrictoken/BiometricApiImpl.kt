package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.ContentType
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiRequest.BiometricRequest
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import javax.inject.Inject

private const val BIOMETRIC_TOKEN_ENDPOINT = "/async/biometricToken"

@CompositionScope
class BiometricApiImpl
    @Inject
    constructor(
        private val httpClient: GenericHttpClient,
        private val configStore: ConfigStore,
    ) : BiometricApi,
        LogTagProvider {
        override suspend fun getBiometricToken(
            sessionId: String,
            documentType: String,
        ): ApiResponse {
            val request =
                ApiRequest.Post(
                    url = "${configStore.readSingle(IdCheckAsyncBackendBaseUrl).value}$BIOMETRIC_TOKEN_ENDPOINT",
                    body =
                        BiometricRequest(
                            sessionId,
                            documentType,
                        ),
                    contentType = ContentType.APPLICATION_JSON,
                )

            return httpClient.makeAuthorisedRequest(
                apiRequest = request,
                scope = SCOPE,
            )
        }

        companion object {
            const val SCOPE = "idCheck.activeSession.read" // check if this is the correct
        }
    }
