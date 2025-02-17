package uk.gov.onelogin.criorchestrator.features.session.internal

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.ResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class SessionApiImpl
    @Inject
    constructor(
        private val resourceProvider: ResourceProvider,
        private val httpClient: GenericHttpClient,
    ) : SessionApi {
        override suspend fun getActiveSession(): ApiResponse {
            val endPoint = SessionApi.Endpoints.ActiveSession.endpoint
            val request =
                ApiRequest.Get(
                    url =
                        resourceProvider.getEnglishString(R.string.backendApiUrl) +
                            endPoint,
                )
            val result =
                httpClient.makeAuthorisedRequest(
                    apiRequest = request,
                    scope = SCOPE,
                )
            return result
        }

        companion object {
            const val SCOPE = "IDCheckBackend"
        }
    }
