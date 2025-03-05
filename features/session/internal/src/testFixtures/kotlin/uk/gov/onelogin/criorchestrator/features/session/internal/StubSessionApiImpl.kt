package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApi

class StubSessionApiImpl : SessionApi {
    private var returnedResponse: ApiResponse = ApiResponse.Success(true)

    fun setActiveSession(response: ApiResponse) {
        returnedResponse = response
    }

    override suspend fun getActiveSession(): ApiResponse = returnedResponse
}
