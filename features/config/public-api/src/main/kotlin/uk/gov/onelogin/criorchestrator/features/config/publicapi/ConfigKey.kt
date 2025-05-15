package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.ImmutableList

/**
 * Base class for configuration keys.
 *
 * @property name The human readable name for this configuration item
 */
abstract class ConfigKey<out T : Config.Value>(
    val name: String,
    val dependsOn: BooleanConfigKey? = null,
) {
    val id: String = this.javaClass.name

    @Throws
    abstract fun requireValidValue(value: Config.Value)
}

abstract class BooleanConfigKey(
    name: String,
    dependsOn: BooleanConfigKey? = null,
) : ConfigKey<Config.Value.BooleanValue>(name) {
    override fun requireValidValue(value: Config.Value) {
        require(value is Config.Value.BooleanValue) {
            "Value for $name must be a BooleanValue"
        }
    }
}

abstract class StringConfigKey(
    name: String,
    dependsOn: BooleanConfigKey? = null,
) : ConfigKey<Config.Value.StringValue>(name, dependsOn) {
    override fun requireValidValue(value: Config.Value) =
        require(value is Config.Value.StringValue) {
            "Value for $name must be a StringValue"
        }
}

abstract class OptionConfigKey(
    name: String,
    val options: ImmutableList<String>,
    dependsOn: BooleanConfigKey? = null,
) : StringConfigKey(name, dependsOn) {
    override fun requireValidValue(value: Config.Value) {
        super.requireValidValue(value)
        require((value as Config.Value.StringValue).value in options) {
            "Value for $name must be one of $options"
        }
    }
}
