package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenProvider
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenProviderImpl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiResponse
import javax.inject.Provider

const val SESSION_ID = "session_id"
const val DOCUMENT_TYPE = "document_type"

class BiometricTokenProviderImplTest {
    private lateinit var biometricTokenProvider: BiometricTokenProvider
    private val biometricApi = mock<BiometricApi>()
    private val logger = mock<Logger>()
    private val json = Json { ignoreUnknownKeys = true }

    @BeforeEach
    fun setup() {
        biometricTokenProvider =
            BiometricTokenProviderImpl(
                biometricApi = Provider { biometricApi },
                logger,
            )
    }

    @Test
    fun `emits loading when api response is loading`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(
                    SESSION_ID,
                    DOCUMENT_TYPE,
                ),
            ).thenReturn(
                ApiResponse.Loading,
            )

            val result = biometricTokenProvider.getBiometricToken(SESSION_ID, DOCUMENT_TYPE).first()
            assert(result is BiometricTokenResult.Loading)
        }

    @Test
    fun `emits error when api response is offline`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(
                    SESSION_ID,
                    DOCUMENT_TYPE,
                ),
            ).thenReturn(
                ApiResponse.Offline,
            )

            val result = biometricTokenProvider.getBiometricToken(SESSION_ID, DOCUMENT_TYPE).first()

            assert(result is BiometricTokenResult.Offline)
        }

    @Test
    fun `emits error when api response is failure`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(
                    SESSION_ID,
                    DOCUMENT_TYPE,
                ),
            ).thenReturn(ApiResponse.Failure(status = 400, error = Exception("error")))

            val result = biometricTokenProvider.getBiometricToken(SESSION_ID, DOCUMENT_TYPE).first()
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

            println(encoded)

            whenever(
                biometricApi.getBiometricToken(
                    SESSION_ID,
                    DOCUMENT_TYPE,
                ),
            ).thenReturn(ApiResponse.Success(encoded))

            val result = biometricTokenProvider.getBiometricToken(SESSION_ID, DOCUMENT_TYPE).first()

            assert(result is BiometricTokenResult.Success)
        }

    @Test
    fun `emits error when api response is success but parsing fails`() =
        runTest {
            whenever(
                biometricApi.getBiometricToken(
                    SESSION_ID,
                    DOCUMENT_TYPE,
                ),
            ).thenReturn(ApiResponse.Success("invalid json"))
            val result = biometricTokenProvider.getBiometricToken(SESSION_ID, DOCUMENT_TYPE).first()
            assert(result is BiometricTokenResult.Error)
        }
}
