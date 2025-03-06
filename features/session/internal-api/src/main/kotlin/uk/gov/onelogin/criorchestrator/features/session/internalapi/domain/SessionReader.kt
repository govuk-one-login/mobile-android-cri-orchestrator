package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

fun interface SessionReader {
    fun isActiveSession(): Flow<Boolean>
}
