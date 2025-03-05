package uk.gov.onelogin.criorchestrator.features.session.internal.network

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.response.ActiveSessionApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class, boundType = SessionReader::class)
class RemoteSessionReader
    @Inject
    constructor(
        private val sessionStore: SessionStore,
        private val sessionApi: SessionApi,
        private val logger: Logger,
    ) : SessionReader,
        LogTagProvider {
        override suspend fun isActiveSession(): Boolean {
            val activeSessionResponse = sessionApi.getActiveSession()
            return when (activeSessionResponse) {
                is ApiResponse.Success<*> -> handleSuccessResponse(activeSessionResponse)
                is ApiResponse.Failure -> handleFailureResponse(activeSessionResponse)
                ApiResponse.Loading -> false
                ApiResponse.Offline -> handleOfflineResponse()
            }
        }

        private fun handleSuccessResponse(response: ApiResponse.Success<*>): Boolean =
            try {
                logger.debug(tag, "Got active session")
                val parsedResponse: ActiveSessionApiResponse.ActiveSessionSuccess =
                    Json.decodeFromString(response.response.toString())
                sessionStore.write(
                    Session(
                        sessionId = parsedResponse.sessionId,
                        redirectUri = parsedResponse.redirectUri,
                        state = parsedResponse.state,
                    ),
                )
                true
            } catch (e: IllegalArgumentException) {
                logger.error(tag, "Failed to parse active session response", e)
                false
            }

        private fun handleFailureResponse(response: ApiResponse.Failure): Boolean {
            logger.error(tag, "Failed to fetch active session", response.error)
            return false
        }

        private fun handleOfflineResponse(): Boolean {
            logger.debug(tag, "Failed to fetch active session - device is offline")
            return false
        }
    }
