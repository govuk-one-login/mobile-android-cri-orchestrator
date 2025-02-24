package uk.gov.onelogin.criorchestrator.features.session.internal.network

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

private const val GET_ACTIVE_SESSION_ENDPOINT = "async/activeSession"

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class, boundType = SessionApi::class)
class SessionApiImpl
    @Inject
    constructor(
        private val httpClient: GenericHttpClient,
        private val configStore: ConfigStore,
        private val logger: Logger,
    ) : SessionApi,
        LogTagProvider {
        override suspend fun getActiveSession(): ApiResponse {
            val backendSessionAsyncUrl = configStore.readValueFromKey("backendAsyncUrl") as String
            logger.debug(
                tag,
                "Calling backend API with backend URL $backendSessionAsyncUrl",
            )
            val request =
                ApiRequest.Get(
                    url = backendSessionAsyncUrl + GET_ACTIVE_SESSION_ENDPOINT,
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
