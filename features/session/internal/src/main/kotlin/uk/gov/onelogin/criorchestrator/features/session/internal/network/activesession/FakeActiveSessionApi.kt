package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import javax.inject.Inject

class FakeActiveSessionApi
    @Inject
    constructor(
        private val configStore: ConfigStore,
    ) : ActiveSessionApi {
        override suspend fun getActiveSession(): ApiResponse {
            val redirectUri =
                when (configStore.readSingle(SdkConfigKey.BypassJourneyType).value) {
                    SdkConfigKey.BypassJourneyType.OPTION_MOBILE_APP_MOBILE -> "\"https://example/redirect\""
                    SdkConfigKey.BypassJourneyType.OPTION_DESKTOP_APP_DESKTOP -> "null"
                    else -> error("Unknown bypass journey type")
                }
            return ApiResponse.Success<String>(
                """
                {
                    "sessionId": "test session ID",
                    "redirectUri": $redirectUri,
                    "state": "11112222333344445555666677778888"
                }
                """.trimIndent(),
            )
        }
    }
