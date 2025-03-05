package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.RemoteSessionReader
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.InMemorySessionStore
import java.util.stream.Stream

class RemoteSessionReaderTest {
    private val sessionApi = StubSessionApiImpl()
    private val logger = SystemLogger()

    private val remoteSessionReader =
        RemoteSessionReader(
            sessionApi = sessionApi,
            logger = logger,
            sessionStore = InMemorySessionStore(logger),
        )

    @ParameterizedTest(name = "{0}")
    @MethodSource("assertCorrectApiResponseHandling")
    fun `session reader returns `(
        apiResponse: ApiResponse,
        logEntry: String?,
        expectedIsActiveSession: Boolean,
    ) = runTest {
        sessionApi.setActiveSession(apiResponse)
        assertEquals(expectedIsActiveSession, remoteSessionReader.isActiveSession())
        logEntry?.let {
            assertTrue(logger.contains(it))
        }
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
                            status = 401,
                            error = Exception("test exception"),
                        ),
                    ),
                    "Failed to fetch active session",
                    false,
                ),
                arguments(
                    named(
                        "false when API response is Loading",
                        ApiResponse.Loading,
                    ),
                    null,
                    false,
                ),
                arguments(
                    named(
                        "false with expected log entry when API response is Offline",
                        ApiResponse.Offline,
                    ),
                    "Failed to fetch active session - device is offline",
                    false,
                ),
                // This test will also fail if the serialization plugin isn't applied
                arguments(
                    named(
                        "true with expected log entry when API response is Success with correct" +
                            "response format",
                        ApiResponse.Success<String>(
                            """
                            {
                                "sessionId": "test session ID",
                                "redirectUri": "https://example/redirect",
                                "state": "11112222333344445555666677778888"
                            }
                            """.trimIndent(),
                        ),
                    ),
                    "Got active session",
                    true,
                ),
                arguments(
                    named(
                        "false with expected log entry when API response is Success but with " +
                            "incorrect response format",
                        ApiResponse.Success<String>(
                            """
                            {
                                "sessionId_WRONG": "test session ID",
                                "redirectUri": "https://example/redirect",
                                "state": "11112222333344445555666677778888"
                            }
                            """.trimIndent(),
                        ),
                    ),
                    "Failed to parse active session response",
                    false,
                ),
            )
    }
}
