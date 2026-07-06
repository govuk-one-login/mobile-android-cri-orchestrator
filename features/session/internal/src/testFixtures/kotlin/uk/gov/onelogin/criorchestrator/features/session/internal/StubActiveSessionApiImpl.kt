package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkingException
import uk.gov.android.network.service.TransportException
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApi

class StubActiveSessionApiImpl : ActiveSessionApi {
    private var returnedResponse: ApiResponse<String, NetworkingException> =
        ApiResponse.Failure(error = TransportException(cause = null))

    fun setActiveSession(response: ApiResponse<String, NetworkingException>) {
        returnedResponse = response
    }

    override suspend fun getActiveSession(): ApiResponse<String, NetworkingException> = returnedResponse
}
