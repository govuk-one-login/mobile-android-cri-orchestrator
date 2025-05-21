package uk.gov.onelogin.criorchestrator.features.session.internal

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApi
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Provider

@OptIn(ExperimentalCoroutinesApi::class)
@CompositionScope
@ContributesBinding(CriOrchestratorScope::class, boundType = SessionReader::class)
class RemoteSessionReader
    @Inject
    constructor(
        private val sessionStore: SessionStore,
        private val activeSessionApi: Provider<ActiveSessionApi>,
        private val logger: Logger,
    ) : SessionReader,
        LogTagProvider {
        private val json: Json by lazy {
            Json {
                ignoreUnknownKeys = true
            }
        }

        override suspend fun isActiveSession(): Boolean {
            val response = activeSessionApi.get().getActiveSession()
            logResponse(response)
            val session = parseSession(response)
            sessionStore.write(session)

            return session != null
        }

        private fun parseSession(response: ApiResponse): Session? {
            if (response !is ApiResponse.Success<*>) {
                return null
            }

            return try {
                val parsedResponse: ActiveSessionApiResponse.ActiveSessionSuccess =
                    json.decodeFromString(response.response.toString())
                Session(
                    sessionId = parsedResponse.sessionId,
                    redirectUri = generateRedirectUri(parsedResponse.redirectUri, parsedResponse.state),
                )
            } catch (e: IllegalArgumentException) {
                logger.error(tag, "Failed to parse active session response", e)
                null
            }
        }

        private fun logResponse(response: ApiResponse) {
            when (response) {
                is ApiResponse.Success<*> ->
                    logger.debug(tag, "Got active session")

                is ApiResponse.Failure ->
                    logger.error(tag, "Failed to fetch active session", response.error)

                ApiResponse.Loading ->
                    logger.debug(tag, "Loading ... fetching active session ...")

                ApiResponse.Offline ->
                    logger.debug(tag, "Failed to fetch active session - device is offline")
            }
        }

        // IPV needs the redirect URI to have the encoded state as a query parameter
        private fun generateRedirectUri(
            redirectUri: String?,
            state: String,
        ): String? =
            if (redirectUri.isNullOrBlank()) {
                null
            } else {
                val encodedState = URLEncoder.encode(state, "utf-8")
                val separator = if (redirectUri.contains('?')) '&' else '?'
                "${redirectUri}${separator}state=$encodedState"
            }
    }
