package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.coroutines.flow.Flow

/**
 * Interface for reading and writing configuration and feature flags.
 */
interface ConfigStore {
    /**
     * Get a value for the given key which which updates when the value changes.
     *
     * @param key The configuration key.
     */
    fun <T : Config.Value> read(key: ConfigKey<T>): Flow<T>

    /**
     * Get value for a given key synchronously once.
     *
     * @param key The configuration key.
     */
    fun <T : Config.Value> readSingle(key: ConfigKey<T>): T

    /**
     * Get the whole configuration.
     */
    fun readAll(): Flow<Config>

    /**
     * Write a single entry of configuration.
     *
     * If the entry already exists, it is overwritten.
     *
     * @param entry the entry to write.
     */
    fun <T : Config.Value> write(entry: Config.Entry<T>)

    /**
     * Write multiple entries of configuration.
     *
     * If any entries already exist, they are overwritten.
     *
     * @param config The configuration to add.
     */
    fun writeAll(config: Config)
}
