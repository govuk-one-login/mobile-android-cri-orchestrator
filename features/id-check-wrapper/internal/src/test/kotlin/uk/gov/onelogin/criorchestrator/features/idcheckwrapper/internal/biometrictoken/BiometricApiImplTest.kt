package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import io.gatehill.imposter.openapi.embedded.OpenApiImposterBuilder
import io.gatehill.imposter.openapi.embedded.OpenApiMockEngine
import org.junit.jupiter.api.BeforeEach
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient

class BiometricApiImplTest {
    lateinit var biometricApiImpl: BiometricApi
    private val fakeConfigStore = FakeConfigStore()

   /* private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }*/

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

   /* @Test
    fun `biometric API implementation returns stubbed response`() =
        runTest {
            val biometricToken = BiometricToken.createTestToken()
            val biometricTokenString = json.encodeToString(biometricToken)

            val expected =
                ApiResponse.Success<String>(
                    biometricTokenString,
                )

            val result = biometricApiImpl.getBiometricToken("sessionId", "documentType")
            assertEquals(expected, result)
        }*/
}

private const val CONFIG_DIR_PROPERTY = "uk.gov.onelogin.criorchestrator.imposterConfigDir"

object Imposter {
    fun createMockEngine(): OpenApiMockEngine =
        OpenApiImposterBuilderImpl()
            .withConfigurationDir(CONFIG_DIR_PROPERTY)
            .startBlocking()
}

class OpenApiImposterBuilderImpl : OpenApiImposterBuilder<OpenApiMockEngine, OpenApiImposterBuilderImpl>()
