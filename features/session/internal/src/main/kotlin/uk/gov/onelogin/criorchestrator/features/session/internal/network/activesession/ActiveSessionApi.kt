package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import uk.gov.android.network.api.ApiResponse

/**
 * The ID Check async backend's 'active session' API.
 *
 * https://github.com/govuk-one-login/mobile-id-check-async
 */
fun interface ActiveSessionApi {
    /**
     * Get the 'active' ID Check session if it exists and hasn't expired.
     *
     * Retrieves the session from the ID Check async API.
     *
     * A session is considered active if:
     * - the user hasn't selected a document, and
     * - the user hasn't aborted the session
     */
    suspend fun getActiveSession(): ApiResponse
}
