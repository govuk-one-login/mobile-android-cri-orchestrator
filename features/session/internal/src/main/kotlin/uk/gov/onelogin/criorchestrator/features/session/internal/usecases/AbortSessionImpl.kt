package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.TransportException
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.abort.AbortSessionApi
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@OptIn(ExperimentalCoroutinesApi::class)
@ContributesBinding(CriOrchestratorScope::class, binding = binding<AbortSession>())
class AbortSessionImpl(
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
            is ApiResponse.Success ->
                logger.info(tag, "Aborted session")

            is ApiResponse.Failure -> {
                if (response.error is TransportException) {
                    logger.debug(tag, "Failed to abort session - device is offline")
                } else {
                    logger.error(tag, "Failed to abort session", response.error)
                }
            }
        }

        val result =
            when (response) {
                is ApiResponse.Failure -> {
                    if (response.error is TransportException) {
                        AbortSession.Result.Error.Offline
                    } else {
                        AbortSession.Result.Error.Unrecoverable(response.error)
                    }
                }
                is ApiResponse.Success -> AbortSession.Result.Success
            }

        when (result) {
            AbortSession.Result.Error.Offline,
            is AbortSession.Result.Error.Unrecoverable,
            -> {
                // Don't clear the session store
            }
            AbortSession.Result.Success -> {
                sessionStore.updateToAborted()
            }
        }

        return result
    }
}
