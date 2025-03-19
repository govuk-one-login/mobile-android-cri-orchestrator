package uk.gov.onelogin.criorchestrator.features.session.internal

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internal.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.session.internal.network.RemoteSessionReader
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApi
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApiImpl
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.InMemorySessionStore
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers
import javax.inject.Provider

@ExperimentalCoroutinesApi
class IntegrationTest {
    private val fakeConfigStore = FakeConfigStore()
    private val logger = SystemLogger()
    private val dispatchers = CoroutineDispatchers.from(UnconfinedTestDispatcher())

    private lateinit var sessionApiImpl: SessionApi
    private lateinit var remoteSessionReader: RemoteSessionReader
    private lateinit var sessionStore: SessionStore

    @BeforeEach
    fun setup() {
        val imposter = Imposter().createImposter()
        sessionStore = InMemorySessionStore(logger)
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString(),
                    ),
            ),
        )
        sessionApiImpl =
            SessionApiImpl(
                createTestHttpClient(),
                fakeConfigStore,
            )

        val sessionApiProvider =
            Provider<SessionApi> {
                sessionApiImpl
            }

        remoteSessionReader =
            RemoteSessionReader(
                configStore = fakeConfigStore,
                dispatchers = dispatchers,
                sessionStore = sessionStore,
                sessionApi = sessionApiProvider,
                logger = logger,
            )
    }

    @Test
    fun `remote session reader active session check returns true from mocked backend`() =
        runTest {
            remoteSessionReader.isActiveSession().test {
                awaitItem().also {
                    val expectedSession =
                        Session(
                            sessionId = "37aae92b-a51e-4f68-b571-8e455fb0ec34",
                            redirectUri = "https://example/redirect",
                            state = "11112222333344445555666677778888",
                        )
                    sessionStore.read().test {
                        assertEquals(
                            expectedSession,
                            awaitItem(),
                        )
                    }
                    assertTrue(it)
                }
                assertTrue(logger.contains("Got active session"))
            }
        }
}
