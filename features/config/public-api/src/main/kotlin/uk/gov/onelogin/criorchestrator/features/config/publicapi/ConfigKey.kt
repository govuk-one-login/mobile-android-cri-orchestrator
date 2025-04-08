package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.ImmutableList

/**
 * Base class for configuration keys.
 *
 * @property name The human readable name for this configuration item
 */
abstract class ConfigKey<out T : Config.Value>(
    val name: String,
) {
    val id: String = this.javaClass.name

    @Throws
    abstract fun requireValidValue(value: Config.Value)
}

abstract class BooleanConfigKey(
    name: String,
) : ConfigKey<Config.Value.BooleanValue>(name) {
    override fun requireValidValue(value: Config.Value) {
        require(value is Config.Value.BooleanValue) {
            "Value for $name must be a BooleanValue"
        }
    }
}

abstract class StringConfigKey(
    name: String,
) : ConfigKey<Config.Value.StringValue>(name) {
    override fun requireValidValue(value: Config.Value) =
        require(value is Config.Value.StringValue) {
            "Value for $name must be a StringValue"
        }
}

abstract class OptionConfigKey(
    name: String,
    val options: ImmutableList<String>,
) : StringConfigKey(name) {
    override fun requireValidValue(value: Config.Value) {
        super.requireValidValue(value)
        require((value as Config.Value.StringValue).value in options) {
            "Value for $name must be one of $options"
        }
    }
}
