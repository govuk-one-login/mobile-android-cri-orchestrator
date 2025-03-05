package uk.gov.onelogin.criorchestrator.testwrapper.network

import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.StubHttpClient

internal fun createHttpClient(): GenericHttpClient =
    StubHttpClient(
        apiResponse =
            ApiResponse.Success<String>(
                """
                {
                    "sessionId": "test session ID",
                    "redirectUri": "https://example/redirect",
                    "state": "11112222333344445555666677778888"
                }
                """.trimIndent(),
            ),
    )
