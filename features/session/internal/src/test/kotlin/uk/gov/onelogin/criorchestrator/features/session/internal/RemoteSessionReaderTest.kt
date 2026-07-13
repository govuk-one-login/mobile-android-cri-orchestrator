package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.ApiResponseException
import uk.gov.android.network.service.NetworkingException
import uk.gov.android.network.service.TransportException
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.androidutils.FakeUriBuilderImpl
import java.util.stream.Stream

@ExperimentalCoroutinesApi
class RemoteSessionReaderTest {
    private val logger = SystemLogger()
    private val activeSessionApi = spy(StubActiveSessionApiImpl())

    private lateinit var remoteSessionReader: SessionReader

    @BeforeEach
    fun setUp() {
        remoteSessionReader =
            RemoteSessionReader(
                activeSessionApi = { activeSessionApi },
                logger = logger,
                uriBuilder = FakeUriBuilderImpl(),
            )
    }

    @AfterEach
    fun tearDown() {
        activeSessionApi.setActiveSession(
            ApiResponse.Failure(error = TransportException(cause = null)),
        )
    }

    @ParameterizedTest(name = "{0} and correctly writes to session store")
    @MethodSource("assertCorrectApiResponseHandling")
    fun `session reader returns `(
        apiResponse: ApiResponse<String, NetworkingException>,
        logEntry: String,
        expectedResult: SessionReader.Result,
    ) = runTest {
        activeSessionApi.setActiveSession(apiResponse)
        val isActiveSession = remoteSessionReader.isActiveSession()
        assertEquals(expectedResult, isActiveSession)
        assertTrue(logger.contains(logEntry))
    }

    @Test
    fun `session API is called just once`() =
        runTest {
            remoteSessionReader.isActiveSession()
            verify(activeSessionApi, times(1)).getActiveSession()
        }

    companion object {
        @JvmStatic
        @Suppress("LongMethod")
        fun assertCorrectApiResponseHandling(): Stream<Arguments> =
            Stream.of(
                arguments(
                    named(
                        "false with expected log entry when API response is Failure",
                        ApiResponse.Failure(
                            error = ApiResponseException("test exception", null),
                            status = 401,
                        ),
                    ),
                    "Failed to fetch active session",
                    SessionReader.Result.Unknown,
                ),
                arguments(
                    named(
                        "false with expected log entry when API response is 404 not found",
                        ApiResponse.Failure(
                            error = ApiResponseException("test exception", null),
                            status = 404,
                        ),
                    ),
                    "Failed to fetch active session",
                    SessionReader.Result.IsNotActive,
                ),
                arguments(
                    named(
                        "false with expected log entry when API response is a transport failure (offline)",
                        ApiResponse.Failure(
                            error = TransportException(cause = null),
                        ),
                    ),
                    "Failed to fetch active session - device is offline",
                    SessionReader.Result.Unknown,
                ),
                // This test will also fail if the serialization plugin isn't applied
                arguments(
                    named(
                        "true with expected log entry when API response is Success with correct " +
                            "response format - with redirectUri (mobile journey)",
                        ApiResponse.Success(
                            response =
                                """
                                {
                                    "sessionId": "test session ID",
                                    "redirectUri": "https://example/redirect",
                                    "state": "11112222333344445555666677778888"
                                }
                                """.trimIndent(),
                            status = 200,
                        ),
                    ),
                    "Got active session",
                    SessionReader.Result.IsActive(
                        Session(
                            sessionId = "test session ID",
                            redirectUri = "https://example/redirect?state=11112222333344445555666677778888",
                        ),
                    ),
                ),
                arguments(
                    named(
                        "true with expected log entry when API response is Success with correct " +
                            "response format - with new parameters",
                        ApiResponse.Success(
                            response =
                                """
                                {
                                    "sessionId": "test session ID",
                                    "redirectUri": "https://example/redirect",
                                    "additionalParameter": true,
                                    "state": "11112222333344445555666677778888"
                                }
                                """.trimIndent(),
                            status = 200,
                        ),
                    ),
                    "Got active session",
                    SessionReader.Result.IsActive(
                        Session(
                            sessionId = "test session ID",
                            redirectUri = "https://example/redirect?state=11112222333344445555666677778888",
                        ),
                    ),
                ),
                arguments(
                    named(
                        "true with expected log entry when API response is Success with correct " +
                            "response format - with existing query parameters on the redirect URI",
                        ApiResponse.Success(
                            response =
                                """
                                {
                                    "sessionId": "test session ID",
                                    "redirectUri": "https://example/redirect?test=test",
                                    "state": "11112222333344445555666677778888"
                                }
                                """.trimIndent(),
                            status = 200,
                        ),
                    ),
                    "Got active session",
                    SessionReader.Result.IsActive(
                        Session(
                            sessionId = "test session ID",
                            redirectUri = "https://example/redirect?test=test&state=11112222333344445555666677778888",
                        ),
                    ),
                ),
                arguments(
                    named(
                        "true with expected log entry when API response is Success with correct " +
                            "response format - with state that requires URI encoding",
                        ApiResponse.Success(
                            response =
                                """
                                {
                                    "sessionId": "test session ID",
                                    "redirectUri": "https://example/redirect",
                                    "state": "&?%:/"
                                }
                                """.trimIndent(),
                            status = 200,
                        ),
                    ),
                    "Got active session",
                    SessionReader.Result.IsActive(
                        Session(
                            sessionId = "test session ID",
                            redirectUri = "https://example/redirect?state=%26%3F%25%3A%2F",
                        ),
                    ),
                ),
                arguments(
                    named(
                        "true with expected log entry when API response is Success with correct " +
                            "response format - no redirectUri (desktop journey)",
                        ApiResponse.Success(
                            response =
                                """
                                {
                                    "sessionId": "test session ID",
                                    "state": "11112222333344445555666677778888"
                                }
                                """.trimIndent(),
                            status = 200,
                        ),
                    ),
                    "Got active session",
                    SessionReader.Result.IsActive(
                        Session(
                            sessionId = "test session ID",
                            redirectUri = null,
                        ),
                    ),
                ),
                arguments(
                    named(
                        "false with expected log entry when API response is Success but with " +
                            "incorrect response format",
                        ApiResponse.Success(
                            response =
                                """
                                {
                                    "sessionId_WRONG": "test session ID",
                                    "redirectUri": "https://example/redirect",
                                    "state": "11112222333344445555666677778888"
                                }
                                """.trimIndent(),
                            status = 200,
                        ),
                    ),
                    "Failed to parse active session response",
                    SessionReader.Result.Unknown,
                ),
            )
    }
}
