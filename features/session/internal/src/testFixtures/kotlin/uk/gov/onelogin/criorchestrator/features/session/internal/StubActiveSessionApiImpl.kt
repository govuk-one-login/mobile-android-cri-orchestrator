package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.android.network.api.v3.ApiResponse
import uk.gov.android.network.service.TransportException
import uk.gov.android.network.service.v2.NetworkServiceResponse
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApi

class StubActiveSessionApiImpl : ActiveSessionApi {
    private var returnedResponse: NetworkServiceResponse =
        ApiResponse.Failure(error = TransportException(cause = null))

    fun setActiveSession(response: NetworkServiceResponse) {
        returnedResponse = response
    }

    override suspend fun getActiveSession(): NetworkServiceResponse = returnedResponse
}
