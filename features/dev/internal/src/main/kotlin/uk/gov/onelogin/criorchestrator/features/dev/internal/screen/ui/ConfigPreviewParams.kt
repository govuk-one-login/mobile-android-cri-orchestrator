package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigKey

internal val previewConfig =
    Config(
        entries =
            persistentListOf(
                Config.Entry<Config.Value.StringValue>(
                    key = PreviewStringConfigKey,
                    value = Config.Value.StringValue("initial value"),
                ),
                Config.Entry<Config.Value.BooleanValue>(
                    key = PreviewBoolConfigKey,
                    value = Config.Value.BooleanValue(value = true),
                ),
            ),
    )

private object PreviewStringConfigKey : ConfigKey<Config.Value.StringValue>(
    name = "Preview string config",
)

private object PreviewBoolConfigKey : ConfigKey<Config.Value.BooleanValue>(
    name = "Preview boolean config",
)
