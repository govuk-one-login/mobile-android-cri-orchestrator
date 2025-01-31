package uk.gov.onelogin.criorchestrator.testwrapper.network

import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.StubHttpClient

internal fun createHttpClient(): GenericHttpClient =
    StubHttpClient(
        apiResponse = ApiResponse.Offline,
    )
