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

    private val keys: List<ConfigKey<Value>> by lazy { entries.map { it.key } }

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

    operator fun <T : Value> get(key: ConfigKey<T>): T {
        val entry = entries.find { it.key == key }

        if (entry == null) {
            throw NoSuchElementException("key: ${key.javaClass.simpleName}")
        }

        require(entry.key == key)
        // The entry guarantees the value's type is consistent with the key
        @Suppress("UNCHECKED_CAST")
        return entry.value as T
    }

    data class Entry<out T : Value>(
        val key: ConfigKey<T>,
        val value: T,
    ) {
        init {
            key.requireValidValue(value)
        }
    }

    sealed interface Value {
        data class StringValue(
            val value: String,
        ) : Value

        data class BooleanValue(
            val value: Boolean,
        ) : Value
    }
}
