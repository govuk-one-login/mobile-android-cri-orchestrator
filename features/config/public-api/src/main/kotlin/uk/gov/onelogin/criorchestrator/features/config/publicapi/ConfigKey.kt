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
}

abstract class OptionConfigKey(
    name: String,
    val options: ImmutableList<String>,
) : ConfigKey<Config.Value.StringValue>(name)
