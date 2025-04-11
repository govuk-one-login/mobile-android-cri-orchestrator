package uk.gov.onelogin.criorchestrator.features.config.publicapi

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

    data object BypassGetTokenAsyncBackend :
        BooleanConfigKey(
            name = "Bypass get token async backend",
        ),
        SdkConfigKey
}
