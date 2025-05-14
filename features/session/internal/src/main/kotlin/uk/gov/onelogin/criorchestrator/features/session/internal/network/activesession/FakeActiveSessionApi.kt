package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import uk.gov.android.network.api.ApiResponse
import javax.inject.Inject

class FakeActiveSessionApi
    @Inject
    constructor() : ActiveSessionApi {
        override suspend fun getActiveSession(): ApiResponse =
            ApiResponse.Success<String>(
                """
                {
                    "sessionId": "test session ID",
                    "redirectUri": "https://example/redirect",
                    "state": "11112222333344445555666677778888"
                }
                """.trimIndent(),
            )
    }
