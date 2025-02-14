package uk.gov.onelogin.criorchestrator.features.session.internal

import android.content.Context
import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.features.session.internalapi.SessionApi
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class SessionApiImpl
    @Inject
    constructor(
        private val context: Context,
        private val httpClient: GenericHttpClient,
    ) : SessionApi {
        override suspend fun getActiveSession(): ApiResponse {
            val endPoint = SessionApi.Endpoints.ActiveSession.endpoint
            val request =
                ApiRequest.Get(
                    url = context.getString(R.string.backendApiUrl) + endPoint,
                )
            val result =
                httpClient.makeAuthorisedRequest(
                    request,
                    "sts-test.hello-world.read",
                )
            return result
        }
    }
