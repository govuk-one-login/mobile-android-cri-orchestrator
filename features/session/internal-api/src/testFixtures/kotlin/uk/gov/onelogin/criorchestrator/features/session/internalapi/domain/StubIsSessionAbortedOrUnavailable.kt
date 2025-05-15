package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StubIsSessionAbortedOrUnavailable(
    val isSessionAbortedOrUnavailable: Boolean,
) : IsSessionAbortedOrUnavailable {
    override fun invoke(): Flow<Boolean> = flowOf(isSessionAbortedOrUnavailable)
}
