package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.ConfigurableBiometricApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val SESSION_ID = "session_id"

class IntegrationTest {
    private val documentType = DocumentVariety.NFC_PASSPORT
    private val configStore = FakeConfigStore()
    private val logger = SystemLogger()
    private val imposter = Imposter.createMockEngine()
    private lateinit var biometricTokenReader: BiometricTokenReader

    @BeforeEach
    fun setup() {
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.BypassIdCheckAsyncBackend,
                value = Config.Value.BooleanValue(false),
            ),
        )

        val biometricApi =
            ConfigurableBiometricApi(
                configStore = configStore,
                realBiometricApi = { BiometricApiImpl(createTestHttpClient(), configStore) },
                fakeBiometricApi = { FakeBiometricTokenApi() },
            )

        biometricTokenReader =
            RemoteBiometricTokenReader(
                biometricApi = biometricApi,
                logger = logger,
            )
    }

    @Test
    fun `when API returns success, it returns a successful response`() {
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString(),
                    ),
            ),
        )
        runTest {
            val expected =
                BiometricTokenResult.Success(
                    BiometricToken(
                        accessToken = "string",
                        opaqueId = "6ec96ea7-941c-4967-9fcf-94fc9b717a22",
                    ),
                )

            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assertEquals(expected, result)
            assertTrue(logger.contains("Got the biometric token"))
        }
    }

    @Test
    fun `when API returns bad request, it returns a failed response`() {
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue(imposter.baseUrl.toString() + "/badRequest"),
            ),
        )

        runTest {
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Error)
        }
    }

    @Test
    fun `when not authorised, it returns a failed response`() {
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue(imposter.baseUrl.toString() + "/unauthorized"),
            ),
        )
        runTest {
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Error)
        }
    }

    @Test
    fun `when API returns an internal server error, it returns a failed response`() {
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue(imposter.baseUrl.toString() + "/internalServerError"),
            ),
        )
        runTest {
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Error)
        }
    }

    @Test
    fun `when API returns an empty response, it returns a failed response`() {
        configStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue(imposter.baseUrl.toString() + "/emptyResponse"),
            ),
        )
        runTest {
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Error)
        }
    }
}
