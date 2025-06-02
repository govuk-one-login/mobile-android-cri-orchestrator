package uk.gov.onelogin.criorchestrator.features.session.publicapi

/**
 * Check for an 'active' ID Check session and update the local session store if it can be determined whether or not
 * the session is active.
 */
fun interface RefreshActiveSession {
    suspend operator fun invoke()
}
