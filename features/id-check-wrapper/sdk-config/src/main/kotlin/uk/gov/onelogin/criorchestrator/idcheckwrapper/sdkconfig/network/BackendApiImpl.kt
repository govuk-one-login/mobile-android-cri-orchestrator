package uk.gov.onelogin.criorchestrator.idcheckwrapper.sdkconfig.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import uk.gov.idcheck.network.api.backend.BackendApi
import uk.gov.idcheck.network.api.checker.OnlineChecker
import uk.gov.idcheck.network.api.di.HttpClientBaseUri
import uk.gov.idcheck.network.api.extensions.HttpStatusCodeExtensions.TransportError
import uk.gov.idcheck.network.api.model.backend.AbortBody
import uk.gov.idcheck.network.api.model.backend.ApiResponse
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import javax.inject.Inject

class BackendApiImpl@Inject
constructor(
    private val client: HttpClient,
    private val logger: Logger,
    @HttpClientBaseUri
    private val baseUrl: String,
    private val onlineChecker: OnlineChecker,
) : BackendApi, LogTagProvider {
    override suspend fun finishBiometricSession(
        authSessionId: String,
        bioSessionId: String,
    ): ApiResponse {
        // DCMAW-11992: v2 /async/finishBiometricSession --------------------------
        if (!isOnline()) {
            return ApiResponse.Offline
        }

        val url = baseUrl + BackendApi.Endpoints.FinishBiometricSession.endpoint
        return try {
            val response =
                client.post(url) {
                    url {
                        parameters.append(BackendApi.AUTH_SESSION_ID_PARAM, authSessionId)
                        parameters.append(BackendApi.BIOMETRIC_SESSION_ID, bioSessionId)
                    }
                }

            if (response.status != HttpStatusCode.OK) {
                throw ResponseException(response, response.body())
            }

            logger.debug(tag, "Successful finish bio session call")
            ApiResponse.Success<String>(response.body())
        } catch (e: ResponseException) {
            logger.error(tag, FINISH_BIOMETRIC_SESSION_ERROR_MSG, e)
            ApiResponse.Failure(
                e.response.status.value,
                Exception(FINISH_BIOMETRIC_SESSION_ERROR_MSG, e),
            )
        } catch (e: Exception) {
            logger.error(tag, FINISH_BIOMETRIC_SESSION_EXCEPTION_MSG, e)
            ApiResponse.Failure(HttpStatusCode.TransportError.value, e)
        }
    }
    // -------------------------------------------------------------------------

    // Not needed anymore since ID Check SDK doesn't call App Info endpoint
    override suspend fun getAppInfo(): ApiResponse = ApiResponse.Offline

    override suspend fun abortJourney(sessionId: String): ApiResponse {
        // DCMAW-11981: v2 /async/abortSession ticket
        if (!isOnline()) {
            return ApiResponse.Offline
        }

        val url = baseUrl + BackendApi.Endpoints.Abort.endpoint
        return try {
            val response =
                client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(AbortBody(sessionId))
                }

            if (response.status != HttpStatusCode.OK) {
                throw ResponseException(response, response.body())
            }

            logger.debug(tag, "Successful abort call")
            ApiResponse.Success<String>(response.body())
        } catch (e: ResponseException) {
            logger.error(tag, ABORT_JOURNEY_ERROR_MSG, e)
            ApiResponse.Failure(e.response.status.value, Exception(ABORT_JOURNEY_ERROR_MSG, e))
        } catch (e: Exception) {
            logger.error(tag, ABORT_JOURNEY_EXCEPTION_MSG, e)
            ApiResponse.Failure(HttpStatusCode.TransportError.value, e)
        }
    }

    private fun isOnline(): Boolean = onlineChecker.isOnline()

    companion object {
        const val FINISH_BIOMETRIC_SESSION_ERROR_MSG =
            "Unsuccessful finish bio session call"
        const val FINISH_BIOMETRIC_SESSION_EXCEPTION_MSG =
            "Exception while calling finish bio session"
        const val ABORT_JOURNEY_ERROR_MSG =
            "Unsuccessful abort call"
        const val ABORT_JOURNEY_EXCEPTION_MSG =
            "Exception while calling abort"
    }
}
