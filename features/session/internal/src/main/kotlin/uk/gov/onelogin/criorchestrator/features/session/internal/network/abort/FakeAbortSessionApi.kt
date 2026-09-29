package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import uk.gov.android.network.api.v3.ApiResponse
import uk.gov.android.network.service.ApiResponseException
import uk.gov.android.network.service.TransportException
import uk.gov.android.network.service.v2.NetworkServiceResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers

private const val BAD_REQUEST = 400
private const val DELAY = 300L

@Inject
class FakeAbortSessionApi(
    private val configStore: ConfigStore,
    private val coroutineDispatchers: CoroutineDispatchers,
) : AbortSessionApi {
    override suspend fun abortSession(sessionId: String): NetworkServiceResponse =
        withContext(coroutineDispatchers.io) {
            delay(DELAY)
            when (configStore.readSingle(SdkConfigKey.BypassAbortSessionApiCall).value) {
                SdkConfigKey.BypassAbortSessionApiCall.OPTION_SUCCESS ->
                    ApiResponse.Success(body = "", status = 200)

                SdkConfigKey.BypassAbortSessionApiCall.OPTION_OFFLINE ->
                    ApiResponse.Failure(error = TransportException(cause = null))

                SdkConfigKey.BypassAbortSessionApiCall.OPTION_UNRECOVERABLE_ERROR ->
                    ApiResponse.Failure(
                        error = ApiResponseException("Simulated exception", null),
                        status = BAD_REQUEST,
                    )

                else -> error("Unknown bypass abort session API call result configuration")
            }
        }
}
