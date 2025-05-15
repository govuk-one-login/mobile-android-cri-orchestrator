package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.ContentType
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import javax.inject.Inject

private const val ABORT_SESSION_ENDPOINT = "/async/abortSession"

class RealAbortSessionApi
    @Inject
    constructor(
        private val httpClient: GenericHttpClient,
        private val configStore: ConfigStore,
    ) : AbortSessionApi,
        LogTagProvider {
        override suspend fun abortSession(sessionId: String): ApiResponse {
            val baseUrl = configStore.read(IdCheckAsyncBackendBaseUrl).first().value
            val request =
                ApiRequest.Post(
                    url = baseUrl + ABORT_SESSION_ENDPOINT,
                    body =
                        AbortSessionApiRequest(
                            sessionId = sessionId,
                        ),
                    contentType = ContentType.APPLICATION_JSON,
                )
            return httpClient.makeRequest(
                apiRequest = request,
            )
        }
    }
