package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.IdCheckWrapperConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

fun Config.Companion.createTestInstance(
    enableManualLauncher: Boolean = false,
    bypassIdCheckAsyncBackend: Boolean = false,
    experimentalComposeNavigation: Boolean = false,
): Config =
    Config(
        entries =
            persistentListOf(
                Config.Entry<Config.Value.BooleanValue>(
                    key = IdCheckWrapperConfigKey.EnableManualLauncher,
                    value = Config.Value.BooleanValue(enableManualLauncher),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = SdkConfigKey.BypassIdCheckAsyncBackend,
                    value = Config.Value.BooleanValue(bypassIdCheckAsyncBackend),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = IdCheckWrapperConfigKey.ExperimentalComposeNavigation,
                    value = Config.Value.BooleanValue(experimentalComposeNavigation),
                ),
                Config.Entry<Config.Value.StringValue>(
                    key = NfcConfigKey.NfcAvailability,
                    value = Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
                ),
            ),
    )
