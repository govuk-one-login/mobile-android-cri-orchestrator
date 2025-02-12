package uk.gov.onelogin.criorchestrator.features.session.internal

import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.features.session.internalapi.BackendApi
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

// TODO: should this sit in it's own networking module? Since this isn't necessarily just session
@ContributesTo(CriOrchestratorScope::class)
class BackendApiImpl
@Inject constructor(
    private val context: Context,
    private val httpClient: GenericHttpClient
) : BackendApi {
    override suspend fun getActiveSession(): ApiResponse {
        val endPoint = BackendApi.Endpoints.ActiveSession.endpoint
        val request = ApiRequest.Get(
            url = context.getString(R.string.backendApiUrl) + endPoint
        )
        val result = httpClient.makeAuthorisedRequest(
            request,
            "sts-test.hello-world.read"
        )
        return result
    }
}