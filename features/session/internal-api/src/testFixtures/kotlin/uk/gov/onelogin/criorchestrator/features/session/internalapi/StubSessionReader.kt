package uk.gov.onelogin.criorchestrator.features.session.internalapi

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader

class StubSessionReader(
    private val isActiveSession: Boolean = true,
) : SessionReader {
    override suspend fun isActiveSession(): Boolean = isActiveSession
}
