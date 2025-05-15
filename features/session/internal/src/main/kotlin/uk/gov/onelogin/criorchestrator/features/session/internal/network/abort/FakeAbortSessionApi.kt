package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import uk.gov.android.network.api.ApiResponse
import javax.inject.Inject

class FakeAbortSessionApi
    @Inject
    constructor() : AbortSessionApi {
        override suspend fun abortSession(sessionId: String): ApiResponse = ApiResponse.Success<String>("")
    }
