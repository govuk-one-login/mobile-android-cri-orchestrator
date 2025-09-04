package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.IdCheckWrapperConfigKey

fun Config.Companion.createTestInstance(
    enableManualLauncher: Boolean = false,
    bypassIdCheckAsyncBackend: Boolean = false,
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
            ),
    )
