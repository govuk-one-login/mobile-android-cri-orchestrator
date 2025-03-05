package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

fun interface SessionReader {
    suspend fun isActiveSession(): Boolean
}
