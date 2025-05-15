package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey

class AbortSessionApiImplTest {
    private val fakeApi = mock<FakeAbortSessionApi>()
    private val realApi = mock<RealAbortSessionApi>()
    private val configStore = FakeConfigStore()

    private val api =
        AbortSessionApiImpl(
            config = configStore,
            realAbortSessionApi = realApi,
            fakeAbortSessionApi = fakeApi,
        )

    private companion object {
        const val SESSION_ID = "sessionId"
    }

    @Test
    fun `given bypass backend config is enabled, it uses the fake api`() =
        runTest {
            givenBypassBackendConfig(true)

            api.abortSession(SESSION_ID)

            verify(fakeApi).abortSession(SESSION_ID)
            verifyNoInteractions(realApi)
        }

    @Test
    fun `given bypass backend config is disabled, it uses the real api`() =
        runTest {
            givenBypassBackendConfig(false)

            api.abortSession(SESSION_ID)

            verify(realApi).abortSession(SESSION_ID)
            verifyNoInteractions(fakeApi)
        }

    private fun givenBypassBackendConfig(value: Boolean) =
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.BypassIdCheckAsyncBackend,
                value =
                    Config.Value.BooleanValue(
                        value = value,
                    ),
            ),
        )
}
