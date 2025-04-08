package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.BooleanConfigKey
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.OptionConfigKey
import uk.gov.onelogin.criorchestrator.features.config.publicapi.StringConfigKey
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui.PreviewOptionConfigKey.OPTION1
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui.PreviewOptionConfigKey.OPTION2
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui.PreviewOptionConfigKey.OPTION3

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
                Config.Entry<Config.Value.StringValue>(
                    key = PreviewOptionConfigKey,
                    value = Config.Value.StringValue(value = OPTION2),
                ),
            ),
    )

private object PreviewStringConfigKey : StringConfigKey(
    name = "Preview string config",
)

private object PreviewBoolConfigKey : BooleanConfigKey(
    name = "Preview boolean config",
)

internal object PreviewOptionConfigKey : OptionConfigKey(
    name = "Preview option config",
    options =
        persistentListOf(
            OPTION1,
            OPTION2,
            OPTION3,
        ),
) {
    const val OPTION1 = "option 1"
    const val OPTION2 = "option 2"
    const val OPTION3 = "option 3"
}
