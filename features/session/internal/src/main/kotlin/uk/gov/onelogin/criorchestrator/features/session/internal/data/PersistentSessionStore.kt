package uk.gov.onelogin.criorchestrator.features.session.internal.data

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

/**
 * Persists the user's ID Check session in a [SecureStoreAsyncV2].
 *
 * An in-memory cache reflects the state of the secure store.
 */
@SingleIn(CriOrchestratorAppScope::class)
@ContributesBinding(CriOrchestratorAppScope::class, binding = binding<SessionStore>())
class PersistentSessionStore(
    private val logger: Logger,
    @param:Named(SessionSecureStoreBindings.STORE_ID)
    private val secureStore: SecureStoreAsyncV2,
    private val coroutineScope: CoroutineScope,
) : SessionStore,
    LogTagProvider {
    private val cache: MutableStateFlow<Session?> = MutableStateFlow(null)

    init {
        coroutineScope.launch {
            load()
        }
    }

    override fun read(): StateFlow<Session?> {
        logger.info(tag, "Subscribing to session from session store")
        return cache.asStateFlow()
    }

    override fun write(value: Session) {
        logger.info(tag, "Writing to session store")
        coroutineScope.launch {
            runCatching {
                secureStore.upsert(KEY_SESSION, Json.encodeToString(value))
            }.onSuccess {
                logger.debug(tag, "New session is $value")
                cache.value = value
            }.onFailure { error ->
                logger.error(tag, "Failed to write session to secure store", error)
            }
        }
    }

    override fun clear() {
        logger.info(tag, "Clearing the session store")
        coroutineScope.launch {
            runCatching {
                secureStore.delete(KEY_SESSION)
            }.onSuccess {
                cache.value = null
            }.onFailure { error ->
                logger.error(tag, "Failed to clear session from secure store", error)
            }
        }
    }

    override fun updateToAborted() =
        updateState {
            advanceAtLeastAborted()
        }

    override fun updateToDocumentSelected() =
        updateState {
            advanceAtLeastDocumentSelected()
        }

    private fun updateState(advance: Session.State.() -> Session.State) {
        val oldState = cache.value
        if (oldState == null) {
            logger.error(tag, "Can't update session because it is null")
            return
        }

        val newState = oldState.copyUpdateState { advance() }

        write(newState)
    }

    /**
     * Try loading the session from the secure store.
     *
     * Falls back to a destructive migration if the action fails.
     */
    private suspend fun load(): Session? =
        runCatching {
            val stored = secureStore.retrieve(KEY_SESSION)
            val json = stored[KEY_SESSION] ?: return null
            Json.decodeFromString<Session>(json)
        }.onSuccess {
            cache.value = it
        }.onFailure { error ->
            logger.error(tag, "Failed to load session from secure store, clearing data", error)
            deleteAll()
        }.getOrNull()

    private suspend fun deleteAll() {
        runCatching {
            secureStore.deleteAll()
        }.onFailure {
            logger.error(tag, "Failed to clear secure store during destructive migration", it)
        }
    }

    internal companion object {
        const val KEY_SESSION = "session"
    }
}
