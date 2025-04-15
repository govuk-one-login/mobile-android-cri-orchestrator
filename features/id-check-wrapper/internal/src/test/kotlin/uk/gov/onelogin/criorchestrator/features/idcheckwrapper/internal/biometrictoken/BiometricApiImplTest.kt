package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import kotlin.test.assertEquals

class BiometricApiImplTest {
    lateinit var biometricApiImpl: BiometricApi
    private val fakeConfigStore = FakeConfigStore()

    @BeforeEach
    fun setup() {
        val imposter = Imposter.createMockEngine()
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString(),
                    ),
            ),
        )

        biometricApiImpl =
            BiometricApiImpl(
                httpClient = createTestHttpClient(),
                configStore = fakeConfigStore,
            )
    }

    @Test
    fun `biometric API implementation returns stubbed response`() =
        runTest {
            val expected =
                ApiResponse.Success<String>(
                    "{\"accessToken\":\"string\",\"opaqueId\":\"6ec96ea7-941c-4967-9fcf-94fc9b717a22\"}",
                )

            val result = biometricApiImpl.getBiometricToken("sessionId", "documentType")
            assertEquals(expected, result)
        }
}
