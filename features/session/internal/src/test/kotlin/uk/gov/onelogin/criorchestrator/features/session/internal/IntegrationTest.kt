package uk.gov.onelogin.criorchestrator.features.session.internal

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.session.internal.network.RemoteSessionReader
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApi
import uk.gov.onelogin.criorchestrator.features.session.internal.network.SessionApiImpl
import uk.gov.onelogin.criorchestrator.features.session.internal.network.data.InMemorySessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import javax.inject.Provider

@ExperimentalCoroutinesApi
class IntegrationTest {
    private val fakeConfigStore = FakeConfigStore()
    private val logger = SystemLogger()
    private val imposter = Imposter.createMockEngine()

    private lateinit var sessionApi: SessionApi
    private lateinit var remoteSessionReader: RemoteSessionReader
    private lateinit var sessionStore: SessionStore

    @BeforeEach
    fun setup() {
        sessionStore = InMemorySessionStore(logger)
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        "",
                    ),
            ),
        )
        sessionApi =
            SessionApiImpl(
                httpClient = createTestHttpClient(),
                configStore = fakeConfigStore,
            )

        remoteSessionReader =
            RemoteSessionReader(
                sessionStore = sessionStore,
                sessionApi = Provider { sessionApi },
                logger = logger,
            )
    }

    @Test
    fun `active session check returns true with correct session details following mocked backend success`() {
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString(),
                    ),
            ),
        )
        runTest {
            assertTrue(remoteSessionReader.isActiveSession())

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
            assertTrue(logger.contains("Got active session"))
        }
    }

    @Test
    fun `active session check returns false following mocked backend failure with correct log`() {
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString() + "/badRequest",
                    ),
            ),
        )
        runTest {
            assertFalse(remoteSessionReader.isActiveSession())
            assertTrue(logger.contains("Failed to fetch active session"))
        }
    }
}
