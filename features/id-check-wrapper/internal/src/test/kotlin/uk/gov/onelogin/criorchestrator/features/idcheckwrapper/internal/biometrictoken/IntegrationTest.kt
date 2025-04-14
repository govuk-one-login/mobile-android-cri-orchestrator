package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val SESSION_ID = "session_id"
private const val DOCUMENT_TYPE = "document_type"

class IntegrationTest {
    private val configStore = FakeConfigStore()
    private val logger = SystemLogger()
    private val imposter = Imposter.createMockEngine()
    private lateinit var biometricApi: BiometricApi
    private lateinit var biometricTokenReader: BiometricTokenReader

    @BeforeEach
    fun setup() {
        biometricApi =
            BiometricApiImpl(
                httpClient = createTestHttpClient(),
                configStore = configStore,
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
                        opaqueId = "string",
                    ),
                )

            val result = biometricTokenReader.getBiometricToken(SESSION_ID, DOCUMENT_TYPE)
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
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, DOCUMENT_TYPE)

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
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, DOCUMENT_TYPE)

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
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, DOCUMENT_TYPE)
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
            val result = biometricTokenReader.getBiometricToken(SESSION_ID, DOCUMENT_TYPE)
            assert(result is BiometricTokenResult.Error)
        }
    }
}
