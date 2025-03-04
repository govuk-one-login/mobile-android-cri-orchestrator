package uk.gov.onelogin.criorchestrator.features.session.internal.network.session

import kotlinx.coroutines.flow.StateFlow
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

/**
 * Interface for handling storing of session. Writing to the [SessionStore] stores the session as an
 * instance of the [Session] class, and reading from the [SessionStore] returns a [StateFlow] of
 * the [Session], allowing both proactive and reactive receipt of the session via [StateFlow.value]
 * and [StateFlow.collect] respectively.
 */
interface SessionStore {
    fun read(): StateFlow<Session?>

    fun write(value: Session?)
}
