package uk.gov.onelogin.criorchestrator.sdk.internal.config

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.IdCheckWrapperConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

private val defaultConfig =
    Config(
        entries =
            persistentListOf(
                Config.Entry<Config.Value.BooleanValue>(
                    key = SdkConfigKey.BypassIdCheckAsyncBackend,
                    Config.Value.BooleanValue(false),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = SdkConfigKey.BypassJourneyType,
                    Config.Value.StringValue(SdkConfigKey.BypassJourneyType.OPTION_MOBILE_APP_MOBILE),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = SdkConfigKey.BypassAbortSessionApiCall,
                    Config.Value.StringValue(SdkConfigKey.BypassAbortSessionApiCall.OPTION_SUCCESS),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = SdkConfigKey.DebugAppReviewPrompts,
                    Config.Value.BooleanValue(false),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = IdCheckWrapperConfigKey.EnableManualLauncher,
                    Config.Value.BooleanValue(false),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = NfcConfigKey.NfcAvailability,
                    Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
                ),
            ),
    )

fun Config.Companion.fromUserConfig(userConfig: Config): Config {
    val initialConfig = defaultConfig.combinedWith(userConfig)

    val requiredConfigKeys =
        listOf(
            SdkConfigKey.BypassIdCheckAsyncBackend,
            SdkConfigKey.IdCheckAsyncBackendBaseUrl,
            SdkConfigKey.DebugAppReviewPrompts,
            IdCheckWrapperConfigKey.EnableManualLauncher,
            NfcConfigKey.NfcAvailability,
        )

    requiredConfigKeys.forEach { configKey ->
        require(initialConfig.keys.any { it == configKey }) {
            "$configKey config must be provided"
        }
    }

    return initialConfig
}
