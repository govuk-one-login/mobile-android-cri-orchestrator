package uk.gov.onelogin.criorchestrator.features.session.internal

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.session.internal.network.RemoteSessionReader
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApi
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.InMemorySessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers
import java.util.stream.Stream
import javax.inject.Provider

@ExperimentalCoroutinesApi
class RemoteSessionReaderTest {
    private val dispatchers = CoroutineDispatchers.from(UnconfinedTestDispatcher())
    private val logger = SystemLogger()
    private val stubSessionApiImpl = StubSessionApiImpl()

    val sessionApi = Provider<SessionApi> { stubSessionApiImpl }

    private lateinit var remoteSessionReader: SessionReader

    @BeforeEach
    fun setUp() {
        val configStore = FakeConfigStore()
        remoteSessionReader =
            RemoteSessionReader(
                configStore = configStore,
                dispatchers = dispatchers,
                sessionStore = InMemorySessionStore(logger),
                sessionApi = sessionApi,
                logger = logger,
            )
    }

    @AfterEach
    fun tearDown() {
        stubSessionApiImpl.setActiveSession(ApiResponse.Offline)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assertCorrectApiResponseHandling")
    fun `session reader returns `(
        apiResponse: ApiResponse,
        logEntry: String,
        expectedIsActiveSession: Boolean,
    ) = runTest {
        stubSessionApiImpl.setActiveSession(apiResponse)
        remoteSessionReader.isActiveSession().test {
            assertEquals(expectedIsActiveSession, awaitItem())
            assertTrue(logger.contains(logEntry))
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
                        "false with expected log entry when API response is Loading",
                        ApiResponse.Loading,
                    ),
                    "Loading ... fetching active session ...",
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
                            "response format - with redirectUri (mobile journey)",
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
                        "true with expected log entry when API response is Success with correct" +
                            "response format - with new parameters",
                        ApiResponse.Success<String>(
                            """
                            {
                                "sessionId": "test session ID",
                                "redirectUri": "https://example/redirect",
                                "additionalParameter": true,
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
                        "true with expected log entry when API response is Success with correct" +
                            "response format - no redirectUri (desktop journey)",
                        ApiResponse.Success<String>(
                            """
                            {
                                "sessionId": "test session ID",
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
