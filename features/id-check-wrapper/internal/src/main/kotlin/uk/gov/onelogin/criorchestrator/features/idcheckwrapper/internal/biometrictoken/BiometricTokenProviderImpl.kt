package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiResponse
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import javax.inject.Inject
import javax.inject.Provider

@ActivityScope
class BiometricTokenProviderImpl
    @Inject
    constructor(
        private val biometricApi: Provider<BiometricApi>,
        private val logger: Logger,
    ) : BiometricTokenProvider,
        LogTagProvider {
        private val json: Json by lazy {
            Json {
                ignoreUnknownKeys = true
            }
        }

        override fun getBiometricToken(
            sessionId: String,
            documentType: String,
        ): Flow<BiometricTokenResult> =
            flow {
                val response = biometricApi.get().getBiometricToken(sessionId, documentType)

                when (response) {
                    is ApiResponse.Loading -> emit(BiometricTokenResult.Loading)

                    ApiResponse.Offline -> emit(BiometricTokenResult.Offline)

                    is ApiResponse.Failure -> {
                        logger.error(
                            tag,
                            "Failed to get biometric token: ${response.error.message}",
                            response.error,
                        )
                        emit(
                            BiometricTokenResult.Error(
                                message = response.error.message ?: "Failed to get biometric token",
                                statusCode = response.status,
                                error = response.error,
                            ),
                        )
                    }

                    is ApiResponse.Success<*> -> {
                        val parsedResponse =
                            json.decodeFromString<BiometricApiResponse.BiometricSuccess>(
                                response.response.toString(),
                            )

                        emit(
                            BiometricTokenResult.Success(
                                BiometricToken(
                                    accessToken = parsedResponse.accessToken,
                                    opaqueId = parsedResponse.opaqueId,
                                ),
                            ),
                        )
                    }
                }
            }.catch { e ->
                logger.error(tag, "Failed to parse biometric token", e)
                emit(
                    BiometricTokenResult.Error(
                        message = "Failed to parse biometric token",
                        error = e as Exception,
                    ),
                )
            }
    }

sealed class BiometricTokenResult {
    object Loading : BiometricTokenResult()

    data class Success(
        val token: BiometricToken,
    ) : BiometricTokenResult()

    object Offline : BiometricTokenResult()

    data class Error(
        val message: String,
        val statusCode: Int? = null,
        val error: Exception? = null,
    ) : BiometricTokenResult()
}
