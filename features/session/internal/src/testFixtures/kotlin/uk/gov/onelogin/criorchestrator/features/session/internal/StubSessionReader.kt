package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader

class StubSessionReader(
    private val isActiveSession: Boolean = true,
) : SessionReader {
    override fun isActiveSession(): Flow<Boolean> = flowOf(isActiveSession)
}
