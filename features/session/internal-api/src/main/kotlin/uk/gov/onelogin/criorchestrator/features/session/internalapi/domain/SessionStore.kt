package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

interface SessionStore {
    fun read(): Flow<Session?>

    suspend fun write(value: Session?)
}
