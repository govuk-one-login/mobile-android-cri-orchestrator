package uk.gov.onelogin.criorchestrator.features.config.publicapi

/**
 * Configuration keys that are relevant to the whole SDK.
 *
 * If you are adding configuration that is relevant to a single feature, consider
 * creating a key within that feature instead.
 */
sealed interface SdkConfigKey {
    data object IdCheckAsyncBackendBaseUrl :
        ConfigKey<Config.Value.StringValue>(
            name = "ID Check async backend base URL",
        ),
        SdkConfigKey
}
