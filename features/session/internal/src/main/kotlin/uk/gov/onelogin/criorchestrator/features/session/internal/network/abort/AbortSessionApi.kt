package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkingException

fun interface AbortSessionApi {
    suspend fun abortSession(sessionId: String): ApiResponse<String, NetworkingException>
}
