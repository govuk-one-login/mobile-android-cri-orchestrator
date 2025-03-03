package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * Holds configuration properties and feature flags.
 *
 * It cannot hold duplicate entries.
 *
 * @property entries The items of configuration
 */
data class Config(
    val entries: ImmutableList<Entry<Value>> = persistentListOf(),
) {
    init {
        // Ensure no duplicate values
        assert(entries.map { it.key }.toSet().size == entries.size)
    }

    val keys: List<ConfigKey<Value>> by lazy { entries.map { it.key } }

    fun combinedWith(config: Config): Config =
        Config(
            entries =
                entries
                    .toMutableList()
                    .apply {
                        removeIf {
                            it.key in config.keys
                        }
                        addAll(
                            config.entries,
                        )
                    }.toPersistentList(),
        )

    data class Entry<out T : Value>(
        val key: ConfigKey<T>,
        val value: T,
    )

    sealed interface Value {
        data class StringValue(
            val value: String,
        ) : Value

        data class BooleanValue(
            val value: Boolean,
        ) : Value
    }
}
