package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeSessionStore(
    session: Session? = Session.createTestInstance(),
) : SessionStore {
    private val sessionFlow: MutableStateFlow<Session?> = MutableStateFlow(session)

    override fun read(): StateFlow<Session?> = sessionFlow

    override fun write(value: Session?) {
        sessionFlow.value = value
    }
}
