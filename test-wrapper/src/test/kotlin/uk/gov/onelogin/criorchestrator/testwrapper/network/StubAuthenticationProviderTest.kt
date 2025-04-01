package uk.gov.onelogin.criorchestrator.testwrapper.network

import android.content.res.Resources
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.auth.AuthenticationResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.StubHttpClient
import uk.gov.onelogin.criorchestrator.testwrapper.R
import uk.gov.onelogin.criorchestrator.testwrapper.network.StubAuthenticationProvider.Companion.GRANT_TYPE
import uk.gov.onelogin.criorchestrator.testwrapper.network.StubAuthenticationProvider.Companion.SUBJECT_TOKEN_TYPE
import kotlin.test.assertEquals
import kotlin.test.fail

class StubAuthenticationProviderTest {
    private val resources = mock<Resources>()

    private val scope = "sts.read.hello-world"
    private val subjectToken = "mock_subject_token"

    @BeforeEach
    fun setup() {
        `when`(resources.getString(R.string.stsUrl)).thenReturn("www.example.com")
    }

    @Test
    fun `it should make a request to exchange the subject token`() {
        val mockClient = mock<GenericHttpClient>()

        val provider =
            StubAuthenticationProvider(
                client = mockClient,
                resources = resources,
                subjectToken = subjectToken,
            )

        runTest {
            provider.fetchBearerToken(scope)

            verify(mockClient).makeRequest(
                ApiRequest.FormUrlEncoded(
                    "www.example.com/token",
                    params =
                        listOf(
                            Pair("grant_type", GRANT_TYPE),
                            Pair("scope", scope),
                            Pair("subject_token", subjectToken),
                            Pair("subject_token_type", SUBJECT_TOKEN_TYPE),
                        ),
                ),
            )
        }
    }

    @Test
    fun `it should correctly return a well-formed authentication response`() {
        val accessToken = "example_access_token"

        val provider =
            StubAuthenticationProvider(
                client =
                    StubHttpClient(
                        ApiResponse.Success(
                            """
                            {
                              "access_token": "$accessToken",
                              "expires_in": 10
                            }
                            """.trimIndent(),
                        ),
                    ),
                resources = resources,
                subjectToken = subjectToken,
            )

        runTest {
            when (val response = provider.fetchBearerToken(scope)) {
                is AuthenticationResponse.Failure ->
                    fail("expected a success response")
                is AuthenticationResponse.Success ->
                    assertEquals(response.bearerToken, accessToken)
            }
        }
    }

    @Test
    fun `it should ignore additional json attributes`() {
        val accessToken = "example_access_token"

        val provider =
            StubAuthenticationProvider(
                client =
                    StubHttpClient(
                        ApiResponse.Success(
                            """
                            {
                              "access_token": "$accessToken",
                              "expires_in": 10,
                              "token_type": "bearer"
                            }
                            """.trimIndent(),
                        ),
                    ),
                resources = resources,
                subjectToken = subjectToken,
            )

        runTest {
            when (val response = provider.fetchBearerToken(scope)) {
                is AuthenticationResponse.Failure ->
                    fail("expected a success response")
                is AuthenticationResponse.Success ->
                    assertEquals(response.bearerToken, accessToken)
            }
        }
    }
}
