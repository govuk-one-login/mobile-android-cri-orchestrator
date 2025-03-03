package uk.gov.onelogin.criorchestrator.features.config.publicapi

data object StubStringConfigKey : ConfigKey<Config.Value.StringValue>(
    name = "stub string config key",
)

data object StubBooleanConfigKey : ConfigKey<Config.Value.BooleanValue>(
    name = "stub boolean config key",
)

fun stubStringConfigEntry(
    key: ConfigKey<Config.Value.StringValue> = StubStringConfigKey,
    value: String,
) = Config.Entry<Config.Value.StringValue>(
    key = key,
    value = Config.Value.StringValue(value = value),
)

fun stubBooleanConfigEntry(
    key: ConfigKey<Config.Value.BooleanValue> = StubBooleanConfigKey,
    value: Boolean,
) = Config.Entry<Config.Value.BooleanValue>(
    key = key,
    value = Config.Value.BooleanValue(value = value),
)
