package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import android.util.Log
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class LauncherDataReader
    @Inject
    constructor(
        private val sessionStore: SessionStore,
        private val biometricTokenReader: BiometricTokenReader,
    ) {
        @OptIn(FlowPreview::class)
        suspend fun read(documentVariety: DocumentVariety): LauncherDataReaderResult? {
            val session =
                sessionStore
                    .read()
                    .filterNotNull()
                    // The session store should return very quickly as it's stored locally.
                    // If there is a timeout, then the session presumably lost due to process death.
                    .timeout(10.seconds)
                    .first()

            val result = biometricTokenReader.getBiometricToken(session.sessionId, documentVariety.name)
            return when (result) {
                is BiometricTokenResult.Error -> {
                    LauncherDataReaderResult.UnRecoverableError(
                        statusCode = result.statusCode,
                        error =
                            DataReaderError(
                                message = "Failed to get biometric token",
                                cause = result.error,
                            ),
                    )
                }

                // The network library is currently not returning this status
                BiometricTokenResult.Loading -> {
                    Log.d("LauncherDataReader", "Loading")
                    null
                }

                // The network library is currently not returning this status
                BiometricTokenResult.Offline -> {
                    LauncherDataReaderResult.RecoverableError(
                        statusCode = null,
                        error =
                            DataReaderError(
                                message = "Device is offline",
                                cause = null,
                            ),
                    )
                }

                is BiometricTokenResult.Success -> {
                    LauncherDataReaderResult.Success(
                        LauncherData(
                            session = session,
                            biometricToken = result.token,
                            documentType = documentVariety.toDocumentType(),
                        ),
                    )
                }
            }
        }
    }

sealed interface LauncherDataReaderResult {
    data class Success(
        val launcherData: LauncherData,
    ) : LauncherDataReaderResult

    data class RecoverableError(
        val error: DataReaderError,
        val statusCode: Int?,
    ) : LauncherDataReaderResult

    data class UnRecoverableError(
        val error: DataReaderError,
        val statusCode: Int?,
    ) : LauncherDataReaderResult
}

data class DataReaderError(
    val message: String,
    val cause: Exception?,
)
