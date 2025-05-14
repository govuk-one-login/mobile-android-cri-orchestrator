package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import uk.gov.android.network.api.ApiResponse

fun interface ActiveSessionApi {
    suspend fun getActiveSession(): ApiResponse
}
