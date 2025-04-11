package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network

import android.util.Log
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network.data.BiometricApiResponse
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import javax.inject.Inject

interface BiometricTokenProvider {
    suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): BiometricToken?
}

@ActivityScope
class BiometricTokenProviderImpl
@Inject
constructor(
    private val biometricApi: BiometricApiImpl,
    private val logger: Logger,
) : BiometricTokenProvider, LogTagProvider {

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    override suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): BiometricToken? {
        Log.d(
            "BiometricTokenProviderImpl",
            "getBiometricToken called with sessionId: $sessionId and documentType: $documentType"
        )

        val response = biometricApi.getBiometricToken(sessionId, documentType)
        return parseBiometricToken(response)
    }

    private fun parseBiometricToken(response: ApiResponse): BiometricToken? {
        if (response !is ApiResponse.Success<*>) {
            return null
        }

        return try {
            val parsedResponse: BiometricApiResponse.BiometricSuccess =
                json.decodeFromString(response.response.toString())

            BiometricToken(
                accessToken = parsedResponse.accessToken,
                opaqueId = parsedResponse.opaqueId
            )

        } catch (e: IllegalArgumentException) {
            logger.error(tag, "")
            null
        }
    }
}