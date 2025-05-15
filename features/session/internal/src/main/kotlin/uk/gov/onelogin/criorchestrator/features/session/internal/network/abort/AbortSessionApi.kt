package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import uk.gov.android.network.api.ApiResponse

fun interface AbortSessionApi {
    suspend fun abortSession(sessionId: String): ApiResponse
}
