package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

fun interface IsSessionAbortedOrUnavailable {
    suspend operator fun invoke(): Flow<Boolean>
}
