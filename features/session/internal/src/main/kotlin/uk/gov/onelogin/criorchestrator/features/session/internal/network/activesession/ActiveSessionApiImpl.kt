package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import javax.inject.Inject

private const val GET_ACTIVE_SESSION_ENDPOINT = "/async/activeSession"

class ActiveSessionApiImpl
    @Inject
    constructor(
        private val httpClient: GenericHttpClient,
        private val configStore: ConfigStore,
    ) : ActiveSessionApi,
        LogTagProvider {
        override suspend fun getActiveSession(): ApiResponse {
            val baseUrl = configStore.read(SdkConfigKey.IdCheckAsyncBackendBaseUrl).first().value
            val request =
                ApiRequest.Get(
                    url = baseUrl + GET_ACTIVE_SESSION_ENDPOINT,
                )
            return httpClient.makeAuthorisedRequest(
                apiRequest = request,
                scope = SCOPE,
            )
        }

        companion object {
            const val SCOPE = "idCheck.activeSession.read"
        }
    }
