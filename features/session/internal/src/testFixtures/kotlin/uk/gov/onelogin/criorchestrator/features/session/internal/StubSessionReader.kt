package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader

class StubSessionReader(
    sessionActiveStatus: Boolean = true,
) : SessionReader {
    override var isActiveSessionStateFlow: StateFlow<Boolean> =
        MutableStateFlow(sessionActiveStatus).asStateFlow()

    override fun handleUpdatedSessionResponse() {
        // Nothing
    }
}
