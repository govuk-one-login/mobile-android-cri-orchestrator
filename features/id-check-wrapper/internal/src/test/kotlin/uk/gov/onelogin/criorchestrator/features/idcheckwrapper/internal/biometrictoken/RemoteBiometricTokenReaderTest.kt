package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.ApiResponseException
import uk.gov.android.network.service.TransportException
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiResponse
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.ConfigurableBiometricApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

private const val SESSION_ID = "session_id"

class RemoteBiometricTokenReaderTest {
    private lateinit var biometricTokenReader: BiometricTokenReader

    private val documentType = DocumentVariety.NFC_PASSPORT
    private val biometricApi = mock<ConfigurableBiometricApi>()
    private val logger = mock<Logger>()
    private val json = Json { ignoreUnknownKeys = true }

    @BeforeEach
    fun setup() {
        biometricTokenReader =
            RemoteBiometricTokenReader(
                biometricApi = biometricApi,
                logger,
            )
    }

    @Test
    fun `emits offline when api response is a transport failure`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(SESSION_ID, documentType),
            ).thenReturn(
                ApiResponse.Failure(error = TransportException(cause = null)),
            )

            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Offline)
        }

    @Test
    fun `emits error when api response is failure`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(SESSION_ID, documentType),
            ).thenReturn(
                ApiResponse.Failure(
                    error = ApiResponseException("error", null),
                    status = 400,
                ),
            )

            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)
            val error = result as BiometricTokenResult.Error

            assert(result == error)
            assert(error.statusCode == 400)
        }

    @Test
    fun `emits success when api response is success`() =
        runTest {
            val successData =
                BiometricApiResponse.BiometricSuccess(
                    accessToken = "SlAV32hkKG",
                    opaqueId = "11111111-1111-1111-1111-111111111111",
                )

            val encoded = json.encodeToString(successData)

            whenever(
                biometricApi.getBiometricToken(SESSION_ID, documentType),
            ).thenReturn(ApiResponse.Success(response = encoded, status = 200))

            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Success)
        }

    @Test
    fun `emits error when api response is success but parsing fails`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(SESSION_ID, documentType),
            ).thenReturn(ApiResponse.Success(response = "invalid json", status = 200))

            val result = biometricTokenReader.getBiometricToken(SESSION_ID, documentType)

            assert(result is BiometricTokenResult.Error)
        }
}
