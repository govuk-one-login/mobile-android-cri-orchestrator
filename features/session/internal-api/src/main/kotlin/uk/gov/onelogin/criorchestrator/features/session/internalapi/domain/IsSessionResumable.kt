package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

/**
 * The user can still resume their session from the web and continue to select a document.
 *
 * The session is also known as 'active'.
 *
 * @see [Session.State.Created]
 */
fun interface IsSessionResumable {
    operator fun invoke(): Flow<Boolean>
}
