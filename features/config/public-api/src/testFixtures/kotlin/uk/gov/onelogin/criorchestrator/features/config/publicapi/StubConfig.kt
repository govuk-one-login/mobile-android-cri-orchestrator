package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data object StubStringConfigKey : ConfigKey<Config.Value.StringValue>(
    name = "stub string config key",
)

const val STUB_STRING_CONFIG_VALUE = "stub string config value"

data object StubBooleanConfigKey : ConfigKey<Config.Value.BooleanValue>(
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
            stubStringConfigEntry(),
            stubBooleanConfigEntry(),
        ),
) = Config(
    entries = entries,
)
