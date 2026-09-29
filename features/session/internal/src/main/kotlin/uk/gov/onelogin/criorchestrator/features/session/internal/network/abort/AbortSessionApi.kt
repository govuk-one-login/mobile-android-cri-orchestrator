package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import uk.gov.android.network.service.v2.NetworkServiceResponse

fun interface AbortSessionApi {
    suspend fun abortSession(sessionId: String): NetworkServiceResponse
}
