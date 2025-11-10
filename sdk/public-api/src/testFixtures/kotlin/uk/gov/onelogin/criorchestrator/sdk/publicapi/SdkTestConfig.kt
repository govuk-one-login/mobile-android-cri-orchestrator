package uk.gov.onelogin.criorchestrator.sdk.publicapi

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.IdCheckWrapperConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

/**
 * Create SDK [Config] for use in tests.
 *
 * All stubs are enabled.
 */
fun Config.Companion.createTestInstance(
    isNfcAvailable: Boolean = true,
    bypassIdCheckAsyncBackend: Boolean = true,
    bypassJourneyType: String = SdkConfigKey.BypassJourneyType.OPTION_MOBILE_APP_MOBILE,
    enableManualIdCheckSdkLauncher: Boolean = true,
    experimentalComposeNavigation: Boolean = false,
): Config =
    Config(
        entries =
            persistentListOf(
                Config.Entry<Config.Value.BooleanValue>(
                    key = SdkConfigKey.BypassIdCheckAsyncBackend,
                    value = Config.Value.BooleanValue(bypassIdCheckAsyncBackend),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = SdkConfigKey.BypassJourneyType,
                    value = Config.Value.StringValue(bypassJourneyType),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                    value = Config.Value.StringValue("http://localhost"),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = NfcConfigKey.NfcAvailability,
                    value =
                        Config.Value.StringValue(
                            when (isNfcAvailable) {
                                true -> NfcConfigKey.NfcAvailability.OPTION_AVAILABLE
                                false -> NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE
                            },
                        ),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = IdCheckWrapperConfigKey.EnableManualLauncher,
                    value = Config.Value.BooleanValue(enableManualIdCheckSdkLauncher),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = IdCheckWrapperConfigKey.ExperimentalComposeNavigation,
                    value = Config.Value.BooleanValue(experimentalComposeNavigation),
                ),
            ),
    )
