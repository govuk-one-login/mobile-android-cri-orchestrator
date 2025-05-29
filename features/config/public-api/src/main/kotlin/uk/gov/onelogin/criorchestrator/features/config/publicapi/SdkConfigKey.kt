package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.toPersistentList
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.BypassJourneyType.OPTION_DESKTOP_APP_DESKTOP
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.BypassJourneyType.OPTION_MOBILE_APP_MOBILE

/**
 * Configuration keys that are relevant to the whole SDK.
 *
 * If you are adding configuration that is relevant to a single feature, consider
 * creating a key within that feature instead.
 *
 * Current keys are:
 * - [IdCheckAsyncBackendBaseUrl]
 * - [BypassIdCheckAsyncBackend]
 */
sealed interface SdkConfigKey {
    data object IdCheckAsyncBackendBaseUrl :
        StringConfigKey(
            name = "ID Check async backend base URL",
        ),
        SdkConfigKey

    data object BypassIdCheckAsyncBackend :
        BooleanConfigKey(
            name = "Bypass ID Check async backend",
        ),
        SdkConfigKey

    data object BypassJourneyType :
        OptionConfigKey(
            name = "Journey type",
            options =
                listOf(
                    OPTION_MOBILE_APP_MOBILE,
                    OPTION_DESKTOP_APP_DESKTOP,
                ).toPersistentList(),
            dependsOn = BypassIdCheckAsyncBackend,
        ),
        SdkConfigKey {
        const val OPTION_MOBILE_APP_MOBILE = "Mobile-app-mobile (MAM)"
        const val OPTION_DESKTOP_APP_DESKTOP = "Desktop-app-desktop (DAD)"
    }

    data object BypassAbortSessionApiCall :
        OptionConfigKey(
            name = "Abort session API",
            options =
                listOf(
                    BypassAbortSessionApiCall.OPTION_SUCCESS,
                    BypassAbortSessionApiCall.OPTION_OFFLINE,
                    BypassAbortSessionApiCall.OPTION_UNRECOVERABLE_ERROR,
                ).toPersistentList(),
            dependsOn = BypassIdCheckAsyncBackend,
        ),
        SdkConfigKey {
        const val OPTION_SUCCESS = "Success"
        const val OPTION_OFFLINE = "Offline error"
        const val OPTION_UNRECOVERABLE_ERROR = "Unrecoverable error"
    }

    data object DebugAppReviewPrompts :
        BooleanConfigKey(
            name = "Debug app review prompts",
        ),
        SdkConfigKey
}
