package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

/**
 * Interface for reader that sits between the use case that wants an active session check and the
 * session API implementation, while also writing to the session store.
 * [handleUpdatedSessionResponse] is responsible for handling the active session API response and
 * updating the value held by the [isActiveSessionStateFlow].
 *
 * @property isActiveSessionStateFlow A [StateFlow] that holds the boolean result of
 * whether there is an active session. This enables the use case to proactively and reactively check
 * whether there is an active session via [StateFlow.value] and [StateFlow.collect] respectively.
 */
interface SessionReader {
    fun isActiveSession(): Flow<Boolean>
}
