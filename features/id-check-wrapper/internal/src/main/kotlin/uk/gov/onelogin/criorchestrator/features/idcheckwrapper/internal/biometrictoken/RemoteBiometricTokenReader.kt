package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data.BiometricApiResponse
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@CompositionScope
@ContributesBinding(CriOrchestratorScope::class, boundType = BiometricTokenReader::class)
class RemoteBiometricTokenReader
    @Inject
    constructor(
        private val biometricApi: BiometricApi,
        private val logger: Logger,
    ) : BiometricTokenReader,
        LogTagProvider {
        private val json: Json by lazy {
            Json {
                ignoreUnknownKeys = true
            }
        }

        override suspend fun getBiometricToken(
            sessionId: String,
            documentType: String,
        ): BiometricTokenResult {
            val response = biometricApi.getBiometricToken(sessionId, documentType)

            return when (response) {
                ApiResponse.Offline -> BiometricTokenResult.Offline

                is ApiResponse.Failure -> {
                    logger.error(
                        tag,
                        "Failed to get biometric token: ${response.error.message}",
                        response.error,
                    )
                    BiometricTokenResult.Error(
                        statusCode = response.status,
                        error = response.error,
                    )
                }

                is ApiResponse.Success<*> -> {
                    try {
                        val parsedResponse =
                            json.decodeFromString<BiometricApiResponse.BiometricSuccess>(
                                response.response.toString(),
                            )

                        logger.debug(tag, "Got the biometric token")

                        BiometricTokenResult.Success(
                            BiometricToken(
                                accessToken = parsedResponse.accessToken,
                                opaqueId = parsedResponse.opaqueId,
                            ),
                        )
                    } catch (e: IllegalArgumentException) {
                        logger.error(tag, "Failed to parse biometric token", e)
                        BiometricTokenResult.Error(
                            error = e,
                        )
                    }
                }

                ApiResponse.Loading ->
                    BiometricTokenResult.Error(
                        error = IllegalStateException("Loading state should not be returned"),
                    )
            }
        }
    }
