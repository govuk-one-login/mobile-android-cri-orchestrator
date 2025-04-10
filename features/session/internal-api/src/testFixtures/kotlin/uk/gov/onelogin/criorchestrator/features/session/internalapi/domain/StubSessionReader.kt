package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

class StubSessionReader(
    private val isActiveSession: Boolean = true,
) : SessionReader {
    override suspend fun isActiveSession(): Boolean = isActiveSession
}
