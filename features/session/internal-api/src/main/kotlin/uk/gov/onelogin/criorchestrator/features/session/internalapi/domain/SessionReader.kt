package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.StateFlow

interface SessionReader {
    val isActiveSessionStateFlow: StateFlow<Boolean>

    fun handleUpdatedSessionResponse()
}
