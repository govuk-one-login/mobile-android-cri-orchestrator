package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class StubIsSessionAbortedOrUnavailable(
    isSessionAbortedOrUnavailable: Boolean,
) : IsSessionAbortedOrUnavailable {
    val state = MutableStateFlow<Boolean>(isSessionAbortedOrUnavailable)

    override fun invoke(): Flow<Boolean> = state
}
