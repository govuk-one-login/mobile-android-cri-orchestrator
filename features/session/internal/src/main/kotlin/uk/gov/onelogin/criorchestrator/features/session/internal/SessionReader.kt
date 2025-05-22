package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

fun interface SessionReader {
    suspend fun isActiveSession(): Result

    sealed interface Result {
        /**
         * The session is 'active', that is, no document has been selected and the session hasn't been aborted.
         */
        data class IsActive(
            val session: Session,
        ) : Result

        /**
         * The session isn't 'active'. It may still be a valid session; the user might have selected a document type.
         * The session may have been aborted.
         */
        object IsNotActive : Result

        /**
         * The session reader wasn't able to determine if the session is still active.
         */
        object Unknown : Result
    }
}
