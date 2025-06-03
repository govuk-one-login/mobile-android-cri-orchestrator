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
import uk.gov.onelogin.criorchestrator.libraries.androidutils.UriBuilder
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Inject
import javax.inject.Provider

private const val NOT_FOUND = 404

@OptIn(ExperimentalCoroutinesApi::class)
@ContributesBinding(CriOrchestratorSingletonScope::class, boundType = SessionReader::class)
class RemoteSessionReader
    @Inject
    constructor(
        private val activeSessionApi: Provider<ActiveSessionApi>,
        private val logger: Logger,
        private val uriBuilder: UriBuilder,
    ) : SessionReader,
        LogTagProvider {
        private val json: Json by lazy {
            Json {
                ignoreUnknownKeys = true
            }
        }

        override suspend fun isActiveSession(): SessionReader.Result {
            val response = activeSessionApi.get().getActiveSession()
            logResponse(response)

            return when (response) {
                ApiResponse.Loading,
                ApiResponse.Offline,
                -> {
                    return SessionReader.Result.Unknown
                }
                is ApiResponse.Failure -> {
                    when (response.status) {
                        NOT_FOUND -> SessionReader.Result.IsNotActive
                        else -> SessionReader.Result.Unknown
                    }
                }
                is ApiResponse.Success<*> -> {
                    val session = parseSession(response)
                    when (session) {
                        null -> SessionReader.Result.Unknown
                        else -> SessionReader.Result.IsActive(session)
                    }
                }
            }
        }

        private fun parseSession(response: ApiResponse.Success<*>): Session? =
            try {
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
                uriBuilder.buildUri(
                    baseUri = redirectUri,
                    queryKey = "state",
                    queryValue = state,
                )
            }
    }
