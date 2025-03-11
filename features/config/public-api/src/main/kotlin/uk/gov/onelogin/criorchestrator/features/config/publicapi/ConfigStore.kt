package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for configuration store, storing feature flags and any properties that need to be
 * configured for testing or mocking purposes.
 * Reading from the configuration store returns a [StateFlow] of associated type. This enables
 * reading from the [ConfigStore] proactively via [StateFlow.value], while also being able to read
 * from the [ConfigStore] reactively via [StateFlow.collect] within a coroutine.
 */
interface ConfigStore {
    fun read(key: ConfigField): StateFlow<Any>

    fun write(config: ConfigProvider)
}
