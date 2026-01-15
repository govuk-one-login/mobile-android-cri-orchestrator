package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApi
import uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession.ActiveSessionApiImpl
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.androidutils.FakeUriBuilderImpl
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import uk.gov.onelogin.criorchestrator.libraries.testing.tags.IntegrationTest

@ExperimentalCoroutinesApi
@IntegrationTest
class IntegrationTest {
    private val fakeConfigStore = FakeConfigStore()
    private val logger = SystemLogger()
    private val imposter = Imposter.createMockEngine()

    private lateinit var activeSessionApi: ActiveSessionApi
    private lateinit var remoteSessionReader: RemoteSessionReader

    @BeforeEach
    fun setup() {
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        "",
                    ),
            ),
        )
        activeSessionApi =
            ActiveSessionApiImpl(
                httpClient = createTestHttpClient(),
                configStore = fakeConfigStore,
            )

        remoteSessionReader =
            RemoteSessionReader(
                activeSessionApi = { activeSessionApi },
                logger = logger,
                uriBuilder = FakeUriBuilderImpl(),
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
            val expectedSession =
                Session(
                    sessionId = "37aae92b-a51e-4f68-b571-8e455fb0ec34",
                    redirectUri = "https://example/redirect?state=11112222333344445555666677778888",
                )
            assertEquals(
                SessionReader.Result.IsActive(expectedSession),
                remoteSessionReader.isActiveSession(),
            )

            assertTrue(logger.contains("Got active session"))
        }
    }

    @Test
    fun `active session check returns unknown following mocked backend 400 bad request failure with correct log`() {
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
            assertEquals(SessionReader.Result.Unknown, remoteSessionReader.isActiveSession())
            assertTrue(logger.contains("Failed to fetch active session"))
        }
    }

    @Test
    fun `active session check returns inactive following mocked backend 404 not found failure with correct log`() {
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString() + "/notFound",
                    ),
            ),
        )
        runTest {
            assertEquals(SessionReader.Result.IsNotActive, remoteSessionReader.isActiveSession())
            assertTrue(logger.contains("Failed to fetch active session"))
        }
    }
}
