package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader

class StubSessionReader(
    private val sessionActiveStatus: Boolean = true,
) : SessionReader {
    override suspend fun isActiveSession(): Boolean = sessionActiveStatus
}
