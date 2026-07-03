package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.service.NetworkService
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.android.network.api.v2.ApiResponse as ApiResponseV2

private const val ABORT_SESSION_ENDPOINT = "/async/abortSession"

@Inject
class RealAbortSessionApi(
    private val networkService: NetworkService,
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
