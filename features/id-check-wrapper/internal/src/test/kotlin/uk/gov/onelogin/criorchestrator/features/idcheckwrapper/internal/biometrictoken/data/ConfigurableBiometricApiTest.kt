package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricApiImpl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.FakeBiometricTokenApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

class ConfigurableBiometricApiTest {
    private val sessionId = "sessionId"
    private val documentVariety = DocumentVariety.DRIVING_LICENCE
    private val configStore = FakeConfigStore()
    private val fakeBiometricApi: FakeBiometricTokenApi = mock()
    private val realBiometricApi: BiometricApiImpl = mock()
    private val api =
        ConfigurableBiometricApi(
            configStore = configStore,
            realBiometricApi = { realBiometricApi },
            fakeBiometricApi = { fakeBiometricApi },
        )

    @AfterEach
    fun tearDown() {
        verifyNoMoreInteractions(realBiometricApi)
        verifyNoMoreInteractions(fakeBiometricApi)
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

            api.getBiometricToken(sessionId, documentVariety)

            verify(fakeBiometricApi).getBiometricToken(sessionId, documentVariety)
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

            api.getBiometricToken(sessionId, documentVariety)

            verify(realBiometricApi).getBiometricToken(sessionId, documentVariety)
        }
}
