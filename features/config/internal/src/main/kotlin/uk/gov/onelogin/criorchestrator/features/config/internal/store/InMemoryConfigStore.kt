package uk.gov.onelogin.criorchestrator.features.config.internal.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigProvider
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore

class InMemoryConfigStore(
    private val logger: Logger,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ConfigStore,
    LogTagProvider {
    private val keyValueMapStateFlow: MutableStateFlow<Map<String, Any>> =
        MutableStateFlow<Map<String, Any>>(
            mapOf(),
        )

    override fun readValueFromKey(key: String): Any {
        val result = keyValueMapStateFlow.asStateFlow().value.getValue(key)
        logger.debug(tag, "Reading value associated with key $key")
        return result
    }

    override fun writeProvidedConfig(configProvider: ConfigProvider) {
        val updatedMap = keyValueMapStateFlow.value.toMutableMap()
        for ((key, value) in configProvider.configMap) {
            updatedMap[key] = value
        }
        logger.debug(
            tag,
            "Writing sets of keys ${updatedMap.keys} with sets " +
                "of values ${updatedMap.values}",
        )
        CoroutineScope(dispatcher).launch {
            keyValueMapStateFlow.emit(updatedMap)
        }
    }
}
