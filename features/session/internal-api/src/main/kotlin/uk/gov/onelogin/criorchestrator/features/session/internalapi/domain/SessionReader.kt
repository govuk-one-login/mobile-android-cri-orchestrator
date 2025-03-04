package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

interface SessionReader {
    fun isActiveSession(): Flow<Boolean>
}
