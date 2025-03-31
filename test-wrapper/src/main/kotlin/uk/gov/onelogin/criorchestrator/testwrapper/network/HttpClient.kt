package uk.gov.onelogin.criorchestrator.testwrapper.network

import android.content.res.Resources
import android.util.Log
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse.Success
import uk.gov.android.network.auth.AuthenticationProvider
import uk.gov.android.network.auth.AuthenticationResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.KtorHttpClient
import uk.gov.android.network.useragent.UserAgentGeneratorStub
import uk.gov.onelogin.criorchestrator.testwrapper.R

internal fun createHttpClient(
    resources: Resources,
    subjectToken: String,
): GenericHttpClient =
    KtorHttpClient(
        userAgentGenerator = UserAgentGeneratorStub("userAgent"),
    ).apply {
        setAuthenticationProvider(
            StubAuthenticationProvider(
                client = this,
                resources = resources,
                subjectToken = subjectToken,
            ),
        )
    }

internal class StubAuthenticationProvider(
    val client: GenericHttpClient,
    val resources: Resources,
    val subjectToken: String,
) : AuthenticationProvider {
    override suspend fun fetchBearerToken(scope: String): AuthenticationResponse {
        val url = resources.getString(R.string.stsUrl) + "/token"

        Log.w("token exchange", "requesting scope: $scope")
        Log.w("token exchange", "from url: $url")

        val request =
            ApiRequest.FormUrlEncoded(
                url = url,
                params =
                    listOf(
                        Pair("grant_type", GRANT_TYPE),
                        Pair("scope", scope),
                        Pair("subject_token", subjectToken),
                        Pair("subject_token_type", SUBJECT_TOKEN_TYPE),
                    ),
            )

        val response = client.makeRequest(request)
        if (response !is Success<*>) {
            Log.w("token exchange", "received response: $response")
            return AuthenticationResponse.Failure(Exception("Could not exchange token with STS mock"))
        }

        val tokenResponse = jsonFormat.decodeFromString<TokenResponse>(response.response.toString())
        return AuthenticationResponse.Success(tokenResponse.accessToken)
    }

    companion object {
        const val GRANT_TYPE = "urn:ietf:params:oauth:grant-type:token-exchange"
        const val SUBJECT_TOKEN_TYPE = "urn:ietf:params:oauth:token-type:access_token"
        private val jsonFormat = Json { ignoreUnknownKeys = true }
    }
}
