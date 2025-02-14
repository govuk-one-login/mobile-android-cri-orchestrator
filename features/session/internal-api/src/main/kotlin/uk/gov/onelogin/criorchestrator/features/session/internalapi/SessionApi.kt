package uk.gov.onelogin.criorchestrator.features.session.internalapi

import uk.gov.android.network.api.ApiResponse

interface SessionApi {
    suspend fun getActiveSession(): ApiResponse

    sealed class Endpoints(
        val endpoint: String,
    ) {
        data object ActiveSession : Endpoints("/activeSession")
    }
}
