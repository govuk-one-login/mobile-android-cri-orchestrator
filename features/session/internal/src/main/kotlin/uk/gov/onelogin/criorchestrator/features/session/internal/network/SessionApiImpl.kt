package uk.gov.onelogin.criorchestrator.features.session.internal.network

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

private const val GET_ACTIVE_SESSION_ENDPOINT = "/async/activeSession"

@ActivityScope
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
            val response =
                httpClient.makeAuthorisedRequest(
                    apiRequest = request,
                    scope = SCOPE,
                )
            return response
        }

        companion object {
            const val SCOPE = "idCheck.activeSession.read"
        }
    }
