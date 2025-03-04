package uk.gov.onelogin.criorchestrator.features.session.internal.network

import uk.gov.android.network.api.ApiResponse

/**
 * Interface for the calling of the active session API.
 */
fun interface SessionApi {
    suspend fun getActiveSession(): ApiResponse
}
