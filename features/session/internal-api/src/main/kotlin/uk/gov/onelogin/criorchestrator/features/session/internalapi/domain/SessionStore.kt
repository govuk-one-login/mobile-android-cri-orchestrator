package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.StateFlow

interface SessionStore {
    fun read(): StateFlow<Session?>

    fun write(value: Session?)
}
