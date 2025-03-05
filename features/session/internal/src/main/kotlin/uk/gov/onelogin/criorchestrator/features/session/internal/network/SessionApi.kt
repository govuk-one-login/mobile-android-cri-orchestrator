package uk.gov.onelogin.criorchestrator.features.session.internal.network

import uk.gov.android.network.api.ApiResponse

fun interface SessionApi {
    suspend fun getActiveSession(): ApiResponse
}
