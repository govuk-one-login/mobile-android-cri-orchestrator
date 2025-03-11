package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger

class InMemoryConfigStore(
    private val logger: Logger,
) : ConfigStore,
    LogTagProvider {
    private val keyValueMap: MutableMap<ConfigField, MutableStateFlow<Any>> = mutableMapOf()

    override fun read(key: ConfigField): StateFlow<Any> {
        val value =
            try {
                keyValueMap.getValue(key).asStateFlow()
            } catch (e: NoSuchElementException) {
                logger.error(
                    tag,
                    "No configuration value provided for ConfigField ${key::class.simpleName}",
                    e,
                )
                throw e
            }
        logger.debug(
            tag,
            "Read key ${key::class.simpleName} with value ${value.value}",
        )
        return value
    }

    override fun write(configProvider: ConfigProvider) {
        updateConfigString(configProvider.backendAsyncUrl, ConfigField.BackendAsyncUrl)
    }

    private fun createStateFlow(value: Any): MutableStateFlow<Any> = MutableStateFlow<Any>(value)

    private fun updateConfigString(
        configProviderString: String,
        configField: ConfigField,
    ) {
        keyValueMap
            .getOrPut(
                configField,
                { createStateFlow(configProviderString) },
            ).value = configProviderString

        logger.debug(
            tag,
            "Config Store written with key ${configField::class.simpleName} and " +
                "value $configProviderString",
        )
    }
}
