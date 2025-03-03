package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
import uk.gov.onelogin.criorchestrator.features.config.internal.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.session.internal.network.RemoteSessionReader
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.InMemorySessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import java.util.stream.Stream

@ExperimentalCoroutinesApi
class RemoteSessionReaderTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private val sessionApi = StubSessionApiImpl()
    private val logger = SystemLogger()

    private lateinit var remoteSessionReader: SessionReader

    @BeforeEach
    fun setUp() {
        val configStore = FakeConfigStore()
        remoteSessionReader =
            RemoteSessionReader(
                configStore = configStore,
                dispatcher = dispatcher,
                sessionStore = InMemorySessionStore(logger),
                sessionApi = sessionApi,
                logger = logger,
            )
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assertCorrectApiResponseHandling")
    fun `session reader returns `(
        apiResponse: ApiResponse,
        logEntry: String,
        expectedIsActiveSession: Boolean,
    ) = runTest {
        sessionApi.setActiveSession(apiResponse)
        remoteSessionReader.handleUpdatedSessionResponse()
        assertEquals(expectedIsActiveSession, remoteSessionReader.isActiveSessionStateFlow.value)
        assertTrue(logger.contains(logEntry))
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
