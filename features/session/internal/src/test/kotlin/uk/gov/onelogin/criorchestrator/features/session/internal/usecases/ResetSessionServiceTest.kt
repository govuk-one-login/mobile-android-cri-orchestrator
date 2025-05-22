package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class ResetSessionServiceTest {
    private val sessionStore =
        FakeSessionStore(
            session = Session.createTestInstance(),
        )
    private val configStore = FakeConfigStore()
    private val resetSessionService = ResetSessionService(sessionStore, configStore)

    @Test
    fun `when config isn't changed, it doesn't clear the session`() =
        runTest {
            val job = launchService()

            sessionStore.read().test {
                assertNotNull(awaitItem())
                expectNoEvents()
            }
            job.cancel()
        }

    @Test
    fun `when base URL changes, it clears the session`() =
        runTest {
            val job = launchService()

            sessionStore.read().test {
                assertNotNull(awaitItem())
                configStore.write(
                    Config.Entry(
                        key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                        value = Config.Value.StringValue("something new"),
                    ),
                )
                assertNull(awaitItem())
                expectNoEvents()
            }
            job.cancel()
        }

    @Test
    fun `when backend bypass setting changes, it clears the session`() =
        runTest {
            val job = launchService()

            sessionStore.read().test {
                assertNotNull(awaitItem())
                configStore.write(
                    Config.Entry(
                        key = SdkConfigKey.BypassIdCheckAsyncBackend,
                        value = Config.Value.BooleanValue(true),
                    ),
                )
                assertNull(awaitItem())
                expectNoEvents()
            }
            job.cancel()
        }

    private suspend fun CoroutineScope.launchService(): Job {
        val job =
            launch {
                resetSessionService.start()
            }
        yield() // Allow the service to consume the initial session
        return job
    }
}
