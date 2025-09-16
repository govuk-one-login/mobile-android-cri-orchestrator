package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey

class ConfigurableActiveSessionApiTest {
    val configStore = FakeConfigStore()
    val fakeActiveSessionApi: FakeActiveSessionApi = mock()
    val realActiveSessionApi: ActiveSessionApiImpl = mock()
    val api =
        ConfigurableActiveSessionApi(
            configStore = configStore,
            realActiveSessionApi = { realActiveSessionApi },
            fakeActiveSessionApi = { fakeActiveSessionApi },
        )

    @AfterEach
    fun tearDown() {
        verifyNoMoreInteractions(realActiveSessionApi)
        verifyNoMoreInteractions(fakeActiveSessionApi)
    }

    @Test
    fun `given config to bypass backend is enabled, it uses fake implementation`() =
        runTest {
            configStore.write(
                Config.Entry(
                    key = SdkConfigKey.BypassIdCheckAsyncBackend,
                    value = Config.Value.BooleanValue(true),
                ),
            )

            api.getActiveSession()

            verify(fakeActiveSessionApi).getActiveSession()
        }

    @Test
    fun `given config to bypass backend is disabled, it uses real implementation`() =
        runTest {
            configStore.write(
                Config.Entry(
                    key = SdkConfigKey.BypassIdCheckAsyncBackend,
                    value = Config.Value.BooleanValue(false),
                ),
            )

            api.getActiveSession()

            verify(realActiveSessionApi).getActiveSession()
        }
}
