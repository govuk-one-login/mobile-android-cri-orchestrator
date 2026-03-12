package uk.gov.onelogin.criorchestrator.features.session.internal.data

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.store.PersistentStore
import uk.gov.onelogin.criorchestrator.libraries.store.Store

/**
 * Persists the user's ID Check session in a [SecureStoreAsyncV2].
 *
 * Delegates storage and caching to [PersistentStore].
 */
@SingleIn(CriOrchestratorAppScope::class)
@ContributesBinding(CriOrchestratorAppScope::class, binding = binding<SessionStore>())
class PersistentSessionStore(
    private val logger: Logger,
    private val persistentStore: PersistentStore<Session>,
) : SessionStore,
    Store<Session> by persistentStore,
    LogTagProvider {
    override suspend fun updateToAborted() {
        updateState { advanceAtLeastAborted() }
    }

    override suspend fun updateToDocumentSelected() {
        updateState { advanceAtLeastDocumentSelected() }
    }

    private suspend fun updateState(advance: Session.State.() -> Session.State) {
        val oldState = persistentStore.read().value
        if (oldState == null) {
            logger.error(tag, "Can't update session because it is null")
            return
        }
        persistentStore.write(oldState.copyUpdateState { advance() })
    }
}
