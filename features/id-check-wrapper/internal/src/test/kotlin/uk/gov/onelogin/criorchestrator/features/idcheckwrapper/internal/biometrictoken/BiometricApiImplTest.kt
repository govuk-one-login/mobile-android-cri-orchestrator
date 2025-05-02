package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.android.network.api.ApiRequest
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.ContentType
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiRequest.BiometricRequest
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import java.util.stream.Stream
import kotlin.test.assertEquals

class BiometricApiImplTest {
    private lateinit var biometricApiImpl: BiometricApi
    private lateinit var imposterBaseUrl: String
    private val fakeConfigStore = FakeConfigStore()

    @BeforeEach
    fun setup() {
        val imposter = Imposter.createMockEngine()
        imposterBaseUrl = imposter.baseUrl.toString()
        fakeConfigStore.write(
            Config.Entry(
                key = IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposterBaseUrl,
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

            val result =
                biometricApiImpl.getBiometricToken("sessionId", DocumentVariety.NFC_PASSPORT)
            assertEquals(expected, result)
        }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assertBackendApiUsesCorrectStringForRequest")
    fun `assert backend API uses correct string for API request given document type`(
        expectedDocumentString: String,
        documentVariety: DocumentVariety,
    ) {
        val mockHttpClient: GenericHttpClient = mock()
        biometricApiImpl =
            BiometricApiImpl(
                httpClient = mockHttpClient,
                configStore = fakeConfigStore,
            )
        runTest {
            whenever(
                mockHttpClient.makeRequest(any()),
            ).thenReturn(
                ApiResponse.Success<String>(
                    "{\"accessToken\":\"string\",\"opaqueId\":\"6ec96ea7-941c-4967-9fcf-94fc9b717a22\"}",
                ),
            )
            biometricApiImpl.getBiometricToken("sessionId", documentVariety)

            verify(
                mockHttpClient,
            ).makeRequest(
                ApiRequest.Post(
                    url = "$imposterBaseUrl/async/biometricToken",
                    body =
                        BiometricRequest(
                            "sessionId",
                            expectedDocumentString,
                        ),
                    contentType = ContentType.APPLICATION_JSON,
                ),
            )
        }
    }

    companion object {
        @JvmStatic
        fun assertBackendApiUsesCorrectStringForRequest(): Stream<Arguments> =
            Stream.of(
                arguments(
                    "NFC_PASSPORT",
                    DocumentVariety.NFC_PASSPORT,
                ),
                arguments(
                    "UK_DRIVING_LICENCE",
                    DocumentVariety.DRIVING_LICENCE,
                ),
                arguments(
                    "UK_NFC_BRP",
                    DocumentVariety.BRP,
                ),
            )
    }
}
