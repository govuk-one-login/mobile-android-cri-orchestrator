package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data object StubStringConfigKey : StringConfigKey(
    name = "stub string config key",
)

const val STUB_STRING_CONFIG_VALUE = "stub string config value"

data object StubOptionConfigKey : OptionConfigKey(
    name = "stub option config key",
    options =
        persistentListOf(
            StubOptionConfigKey.OPTION_1,
            StubOptionConfigKey.OPTION_2,
            StubOptionConfigKey.OPTION_3,
        ),
) {
    const val OPTION_1 = "option 1"
    const val OPTION_2 = "option 2"
    const val OPTION_3 = "option 3"
}

data object StubBooleanConfigKey : BooleanConfigKey(
    name = "stub boolean config key",
)

const val STUB_BOOLEAN_CONFIG_VALUE = true

fun stubStringConfigEntry(
    key: ConfigKey<Config.Value.StringValue> = StubStringConfigKey,
    value: String = STUB_STRING_CONFIG_VALUE,
) = Config.Entry<Config.Value.StringValue>(
    key = key,
    value = Config.Value.StringValue(value = value),
)

fun stubOptionConfigEntry(
    key: OptionConfigKey = StubOptionConfigKey,
    value: String = StubOptionConfigKey.OPTION_1,
) = Config.Entry<Config.Value.StringValue>(
    key = key,
    value = Config.Value.StringValue(value = value),
)

fun stubBooleanConfigEntry(
    key: ConfigKey<Config.Value.BooleanValue> = StubBooleanConfigKey,
    value: Boolean = STUB_BOOLEAN_CONFIG_VALUE,
) = Config.Entry<Config.Value.BooleanValue>(
    key = key,
    value = Config.Value.BooleanValue(value = value),
)

fun stubConfig(
    entries: ImmutableList<Config.Entry<Config.Value>> =
        persistentListOf(
            stubBooleanConfigEntry(),
            stubStringConfigEntry(),
        ),
) = Config(
    entries = entries,
)
