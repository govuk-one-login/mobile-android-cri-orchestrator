package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.service.NetworkService
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.android.network.api.v2.ApiResponse as ApiResponseV2

private const val GET_ACTIVE_SESSION_ENDPOINT = "/async/activeSession"

@Inject
class ActiveSessionApiImpl(
    private val networkService: NetworkService,
    private val configStore: ConfigStore,
) : ActiveSessionApi,
    LogTagProvider {
    override suspend fun getActiveSession(): ApiResponse {
        val baseUrl = configStore.read(SdkConfigKey.IdCheckAsyncBackendBaseUrl).first().value
        val request =
            ApiRequest.Get(
                url = baseUrl + GET_ACTIVE_SESSION_ENDPOINT,
            )
        return when (val response = networkService.makeRequest(request) {
            withAuthentication(SCOPE)
        }) {
            is ApiResponseV2.Success -> ApiResponse.Success(response.response)
            is ApiResponseV2.Failure -> ApiResponse.Failure(
                status = response.status ?: 0,
                error = response.error,
            )
        }
    }

    companion object {
        const val SCOPE = "idCheck.activeSession.read"
    }
}
