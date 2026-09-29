package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.service.v2.NetworkService
import uk.gov.android.network.service.v2.NetworkServiceResponse
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl

private const val ABORT_SESSION_ENDPOINT = "/async/abortSession"

@Inject
class RealAbortSessionApi(private val networkService: NetworkService, private val configStore: ConfigStore) :
    AbortSessionApi,
    LogTagProvider {
    override suspend fun abortSession(sessionId: String): NetworkServiceResponse {
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
        return networkService.makeRequest(request)
    }
}
