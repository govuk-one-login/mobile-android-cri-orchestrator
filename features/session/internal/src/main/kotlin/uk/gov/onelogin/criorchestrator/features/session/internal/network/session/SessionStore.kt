package uk.gov.onelogin.criorchestrator.features.session.internal.network.session

import kotlinx.coroutines.flow.StateFlow
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

interface SessionStore {
    fun read(): StateFlow<Session>

    fun write(value: Session)
}
