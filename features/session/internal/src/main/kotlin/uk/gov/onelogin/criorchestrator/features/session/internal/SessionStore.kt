package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.flow.StateFlow

interface SessionStore {
    fun read(): StateFlow<Boolean>

    fun write(value: Boolean)
}
