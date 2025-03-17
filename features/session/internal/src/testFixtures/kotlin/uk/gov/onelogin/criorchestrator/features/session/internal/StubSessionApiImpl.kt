package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApi

object StubSessionApiImpl : SessionApi {
    private var returnedResponse: ApiResponse = ApiResponse.Offline

    fun setActiveSession(response: ApiResponse) {
        returnedResponse = response
    }

    override suspend fun getActiveSession(): ApiResponse = returnedResponse
}
