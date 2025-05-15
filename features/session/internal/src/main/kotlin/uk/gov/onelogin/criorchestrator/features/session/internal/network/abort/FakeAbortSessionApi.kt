package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import javax.inject.Inject

private const val BAD_REQUEST = 400

class FakeAbortSessionApi
    @Inject
    constructor(
        private val configStore: ConfigStore,
    ) : AbortSessionApi {
        override suspend fun abortSession(sessionId: String): ApiResponse =
            when (configStore.readSingle(SdkConfigKey.BypassAbortSessionApiCall).value) {
                SdkConfigKey.BypassAbortSessionApiCall.OPTION_SUCCESS -> ApiResponse.Success<String>("")
                SdkConfigKey.BypassAbortSessionApiCall.OPTION_OFFLINE -> ApiResponse.Offline
                SdkConfigKey.BypassAbortSessionApiCall.OPTION_UNRECOVERABLE_ERROR ->
                    ApiResponse.Failure(
                        BAD_REQUEST,
                        Exception("Simulated exception"),
                    )

                else -> error("Unknown bypass abort session API call result configuration")
            }
    }
