package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data object StubStringConfigKey : ConfigKey<Config.Value.StringValue>(
    name = "stub string config key",
)

data object StubBooleanConfigKey : ConfigKey<Config.Value.BooleanValue>(
    name = "stub boolean config key",
)

fun stubStringConfigEntry(
    key: ConfigKey<Config.Value.StringValue> = StubStringConfigKey,
    value: String = "stub string config value",
) = Config.Entry<Config.Value.StringValue>(
    key = key,
    value = Config.Value.StringValue(value = value),
)

fun stubBooleanConfigEntry(
    key: ConfigKey<Config.Value.BooleanValue> = StubBooleanConfigKey,
    value: Boolean = true,
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
