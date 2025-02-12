package uk.gov.onelogin.criorchestrator.features.session.internalapi

import uk.gov.android.network.api.ApiResponse

// TODO: does this need it's own component?
interface BackendApi {
    suspend fun getActiveSession(): ApiResponse

    sealed class Endpoints(val endpoint: String) {
        data object ActiveSession : Endpoints("/activeSession")
    }
}