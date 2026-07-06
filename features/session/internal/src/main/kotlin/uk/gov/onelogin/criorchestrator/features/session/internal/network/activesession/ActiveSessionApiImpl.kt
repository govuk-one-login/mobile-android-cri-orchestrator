package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkService
import uk.gov.android.network.service.NetworkingException
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey

private const val GET_ACTIVE_SESSION_ENDPOINT = "/async/activeSession"

@Inject
class ActiveSessionApiImpl(
    private val networkService: NetworkService,
    private val configStore: ConfigStore,
) : ActiveSessionApi,
    LogTagProvider {
    override suspend fun getActiveSession(): ApiResponse<String, NetworkingException> {
        val baseUrl = configStore.read(SdkConfigKey.IdCheckAsyncBackendBaseUrl).first().value
        val request =
            ApiRequest.Get(
                url = baseUrl + GET_ACTIVE_SESSION_ENDPOINT,
            )
        return networkService.makeRequest(request) {
            withAuthentication(SCOPE)
        }
    }

    companion object {
        const val SCOPE = "idCheck.activeSession.read"
    }
}
