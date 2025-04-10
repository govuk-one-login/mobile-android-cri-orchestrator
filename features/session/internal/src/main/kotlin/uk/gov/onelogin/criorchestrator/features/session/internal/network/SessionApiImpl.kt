package uk.gov.onelogin.criorchestrator.features.session.internal.network

import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import javax.inject.Inject

private const val GET_ACTIVE_SESSION_ENDPOINT = "/async/activeSession"

@CompositionScope
class SessionApiImpl
    @Inject
    constructor(
        private val httpClient: GenericHttpClient,
        private val configStore: ConfigStore,
    ) : SessionApi,
        LogTagProvider {
        override suspend fun getActiveSession(): ApiResponse {
            val baseUrl = configStore.read(IdCheckAsyncBackendBaseUrl).first().value
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
