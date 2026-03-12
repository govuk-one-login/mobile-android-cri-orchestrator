package uk.gov.onelogin.criorchestrator.libraries.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger

/**
 * A [SecureStoreAsyncV2] backed store with an in-memory cache.
 *
 * If the initial load fails, the data is deleted as a destructive migration.
 */
open class PersistentStore<T>(
    private val secureStore: SecureStoreAsyncV2,
    private val key: String,
    private val logger: Logger,
    coroutineScope: CoroutineScope,
    private val serializer: KSerializer<T>,
) : Store<T>,
    LogTagProvider {
    private val cache = MutableStateFlow<T?>(null)
    private val ready: Job =
        coroutineScope.launch {
            runCatching {
                secureStore.retrieve(key)[key]?.let {
                    Json.decodeFromString(serializer, it)
                }
            }.onSuccess {
                cache.value = it
            }.onFailure { error ->
                logger.error(tag, "Failed to load $key from secure store, clearing data", error)
                runCatching {
                    secureStore.delete(key)
                }.onFailure {
                    logger.error(tag, "Failed to clear $key secure store during destructive migration", it)
                }
            }
        }

    override suspend fun read(): StateFlow<T?> {
        ready.join()
        logger.info(tag, "Subscribing to $key secure store")
        return cache.asStateFlow()
    }

    override suspend fun write(value: T) {
        ready.join()
        logger.info(tag, "Writing to $key store")
        logger.debug(tag, "New $key is $value")
        runCatching {
            secureStore.upsert(key, Json.encodeToString(serializer, value))
        }.onSuccess {
            cache.value = value
        }.onFailure { error ->
            logger.error(tag, "Failed to write $key secure store", error)
        }
    }

    override suspend fun clear() {
        ready.join()
        runCatching {
            secureStore.delete(key)
        }.onSuccess {
            cache.value = null
        }.onFailure { error ->
            logger.error(tag, "Failed to clear $key from secure store", error)
        }
    }
}
