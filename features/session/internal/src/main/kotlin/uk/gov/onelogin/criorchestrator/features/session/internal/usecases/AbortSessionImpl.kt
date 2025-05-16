package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.abort.AbortSessionApi
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ContributesBinding(CriOrchestratorScope::class, boundType = AbortSession::class)
class AbortSessionImpl
    @Inject
    constructor(
        private val sessionStore: SessionStore,
        private val abortSessionApi: AbortSessionApi,
        private val logger: Logger,
    ) : AbortSession,
        LogTagProvider {
        override suspend fun invoke(): AbortSession.Result {
            val session = sessionStore.read().first()

            if (session == null) {
                logger.error(tag, "Tried to abort a non-existent session")

                // Fail gracefully in case the session has somehow been aborted already
                return AbortSession.Result.Success
            }

            val response = abortSessionApi.abortSession(session.sessionId)

            when (response) {
                is ApiResponse.Success<*> ->
                    logger.info(tag, "Aborted session")

                is ApiResponse.Failure ->
                    logger.error(tag, "Failed to abort session", response.error)

                ApiResponse.Loading -> unexpectedLoadingApiResponse()

                ApiResponse.Offline ->
                    logger.debug(tag, "Failed to abort session - device is offline")
            }

            val result =
                when (response) {
                    is ApiResponse.Failure -> AbortSession.Result.Error.Unrecoverable(response.error)
                    ApiResponse.Offline -> AbortSession.Result.Error.Offline
                    is ApiResponse.Success<*> -> AbortSession.Result.Success
                    ApiResponse.Loading -> unexpectedLoadingApiResponse()
                }

            when (result) {
                AbortSession.Result.Error.Offline -> {
                    // Don't clear the session store
                }
                is AbortSession.Result.Error.Unrecoverable,
                AbortSession.Result.Success,
                -> {
                    sessionStore.write(null)
                }
            }

            return result
        }

        /**
         * This should never be called as the networking library doesn't emit loading results.
         */
        private fun unexpectedLoadingApiResponse(): Nothing = error("Loading state is not possible")
    }
