package uk.gov.onelogin.criorchestrator.features.session.internal.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

class FakeSessionStore : SessionStore {
    private val sessions = MutableSharedFlow<Session?>()

    override fun read(): Flow<Session?> = sessions.asSharedFlow()

    override suspend fun write(value: Session?) {
        sessions.emit(value)
    }
}
