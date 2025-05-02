package uk.gov.onelogin.criorchestrator.testwrapper.network

import android.content.res.Resources
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse.Success
import uk.gov.android.network.auth.AuthenticationProvider
import uk.gov.android.network.auth.AuthenticationResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.KtorHttpClient
import uk.gov.android.network.useragent.UserAgentGeneratorStub
import uk.gov.onelogin.criorchestrator.testwrapper.R
import uk.gov.onelogin.criorchestrator.testwrapper.SubjectTokenRepository
import javax.inject.Provider

internal fun createHttpClient(
    subjectTokenRepository: SubjectTokenRepository,
    resources: Resources,
): GenericHttpClient =
    KtorHttpClient(
        userAgentGenerator = UserAgentGeneratorStub("userAgent"),
    ).apply {
        setAuthenticationProvider(
            TestWrapperAuthenticationProvider(
                subjectTokenRepository = subjectTokenRepository,
                mockStsAuthenticationProvider =
                    MockStsAuthenticationProvider(
                        resources = resources,
                        client = Provider { this },
                    ),
                stubAuthenticationProvider = StubAuthenticationProvider(),
            ),
        )
    }

internal fun createStubHttpClient(): GenericHttpClient =
    KtorHttpClient(
        userAgentGenerator = UserAgentGeneratorStub("userAgent"),
    ).apply {
        setAuthenticationProvider(
            StubAuthenticationProvider(),
        )
    }

internal class TestWrapperAuthenticationProvider(
    private val mockStsAuthenticationProvider: MockStsAuthenticationProvider,
    private val stubAuthenticationProvider: StubAuthenticationProvider,
    private val subjectTokenRepository: SubjectTokenRepository,
) : AuthenticationProvider {
    override suspend fun fetchBearerToken(scope: String): AuthenticationResponse {
        val sub = subjectTokenRepository.subjectToken
        return if (sub == null) {
            stubAuthenticationProvider.fetchBearerToken(scope)
        } else {
            mockStsAuthenticationProvider.fetchBearerToken(scope, sub)
        }
    }
}

internal class MockStsAuthenticationProvider(
    val client: Provider<GenericHttpClient>,
    val resources: Resources,
) {
    suspend fun fetchBearerToken(
        scope: String,
        subjectToken: String,
    ): AuthenticationResponse {
        val tokenEndpoint = resources.getString(R.string.stsUrl) + "/token"

        val request =
            ApiRequest.FormUrlEncoded(
                url = tokenEndpoint,
                params =
                    listOf(
                        Pair("grant_type", GRANT_TYPE),
                        Pair("scope", scope),
                        Pair("subject_token", subjectToken),
                        Pair("subject_token_type", SUBJECT_TOKEN_TYPE),
                    ),
            )

        val response = client.get().makeRequest(request)
        if (response !is Success<*>) {
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

internal class StubAuthenticationProvider : AuthenticationProvider {
    override suspend fun fetchBearerToken(scope: String): AuthenticationResponse =
        AuthenticationResponse
            .Success("token")
}
