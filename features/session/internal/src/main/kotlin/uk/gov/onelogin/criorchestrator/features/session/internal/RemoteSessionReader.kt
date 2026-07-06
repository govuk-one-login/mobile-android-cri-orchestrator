package uk.gov.onelogin.criorchestrator.features.session.internal

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkingException
import uk.gov.android.network.service.TransportException
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApi
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.androidutils.UriBuilder
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

private const val NOT_FOUND = 404

@OptIn(ExperimentalCoroutinesApi::class)
@ContributesBinding(CriOrchestratorAppScope::class, binding = binding<SessionReader>())
class RemoteSessionReader(
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
        val response = activeSessionApi().getActiveSession()
        logResponse(response)

        return when (response) {
            is ApiResponse.Failure -> {
                if (response.error is TransportException) {
                    SessionReader.Result.Unknown
                } else {
                    when (response.status) {
                        NOT_FOUND -> SessionReader.Result.IsNotActive
                        else -> SessionReader.Result.Unknown
                    }
                }
            }
            is ApiResponse.Success -> {
                val session = parseSession(response)
                when (session) {
                    null -> SessionReader.Result.Unknown
                    else -> SessionReader.Result.IsActive(session)
                }
            }
        }
    }

    private fun parseSession(response: ApiResponse.Success<String>): Session? =
        try {
            val parsedResponse: ActiveSessionApiResponse.ActiveSessionSuccess =
                json.decodeFromString(response.response)
            Session(
                sessionId = parsedResponse.sessionId,
                redirectUri = generateRedirectUri(parsedResponse.redirectUri, parsedResponse.state),
            )
        } catch (e: IllegalArgumentException) {
            logger.error(tag, "Failed to parse active session response", e)
            null
        }

    private fun logResponse(response: ApiResponse<String, NetworkingException>) {
        when (response) {
            is ApiResponse.Success ->
                logger.debug(tag, "Got active session")

            is ApiResponse.Failure -> {
                if (response.error is TransportException) {
                    logger.debug(tag, "Failed to fetch active session - device is offline")
                } else {
                    logger.error(
                        tag,
                        "Failed to fetch active session",
                        response.error,
                    )
                }
            }
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
