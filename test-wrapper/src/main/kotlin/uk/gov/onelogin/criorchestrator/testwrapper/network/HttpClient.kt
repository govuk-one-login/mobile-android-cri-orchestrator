package uk.gov.onelogin.criorchestrator.testwrapper.network

import android.content.res.Resources
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.v2.ApiRequest
import uk.gov.android.network.auth.AuthenticationProvider
import uk.gov.android.network.auth.AuthenticationResponse
import uk.gov.android.network.client.KtorHttpClient
import uk.gov.android.network.service.DefaultNetworkService
import uk.gov.android.network.service.NetworkService
import uk.gov.android.network.useragent.UserAgentGeneratorStub
import uk.gov.onelogin.criorchestrator.testwrapper.R
import uk.gov.onelogin.criorchestrator.testwrapper.SubjectTokenRepository
import javax.inject.Provider
import uk.gov.android.network.api.v2.ApiResponse as ApiResponseV2

internal fun createHttpClient(
    subjectTokenRepository: SubjectTokenRepository,
    resources: Resources,
): NetworkService =
    DefaultNetworkService(
        KtorHttpClient(
            userAgentGenerator = UserAgentGeneratorStub("userAgent"),
        ),
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

internal fun createStubHttpClient(): NetworkService =
    DefaultNetworkService(
        KtorHttpClient(
            userAgentGenerator = UserAgentGeneratorStub("userAgent"),
        ),
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
    val client: Provider<NetworkService>,
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
        if (response !is ApiResponseV2.Success) {
            return AuthenticationResponse.Failure(Exception("Could not exchange token with STS mock"))
        }

        val tokenResponse = jsonFormat.decodeFromString<TokenResponse>(response.response)
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
