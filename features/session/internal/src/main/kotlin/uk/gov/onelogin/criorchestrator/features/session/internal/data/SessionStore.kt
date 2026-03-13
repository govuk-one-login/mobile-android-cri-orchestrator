package uk.gov.onelogin.criorchestrator.features.session.internal.data

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.store.Store

/**
 * Stores the user's ID Check session.
 */
@SingleIn(CriOrchestratorAppScope::class)
@ContributesBinding(CriOrchestratorAppScope::class, binding = binding<SessionStore>())
class SessionStore(
    private val logger: Logger,
    private val store: Store<Session>,
) : SessionStore,
    Store<Session> by store,
    LogTagProvider {
    override suspend fun updateToAborted() {
        updateState { advanceAtLeastAborted() }
    }

    override suspend fun updateToDocumentSelected() {
        updateState { advanceAtLeastDocumentSelected() }
    }

    private suspend fun updateState(advance: Session.State.() -> Session.State) {
        val oldState = store.read().value
        if (oldState == null) {
            logger.error(tag, "Can't update session because it is null")
            return
        }
        store.write(oldState.copyUpdateState { advance() })
    }
}
