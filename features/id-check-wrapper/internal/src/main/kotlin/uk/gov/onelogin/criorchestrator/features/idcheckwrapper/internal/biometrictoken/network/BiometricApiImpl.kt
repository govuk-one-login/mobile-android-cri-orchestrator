package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network

import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.ContentType
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network.data.BiometricApiRequest.BiometricRequest
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import javax.inject.Inject

private const val BIOMETRIC_TOKEN_ENDPOINT = "/async/biometricToken"

@ActivityScope
class BiometricApiImpl
@Inject
constructor(
    private val httpClient: GenericHttpClient,
) : BiometricApi, LogTagProvider {
    override suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): ApiResponse {
        val request = ApiRequest.Post(
            url = BIOMETRIC_TOKEN_ENDPOINT,
            body = BiometricRequest(
                sessionId,
                documentType,
            ),
            contentType = ContentType.APPLICATION_JSON,
        )

        return httpClient.makeAuthorisedRequest(
            apiRequest = request,
            scope = SCOPE,
        )
    }

    companion object {
        const val SCOPE = "idCheck.activeSession.read" // TODO: check if this is the correct
    }
}