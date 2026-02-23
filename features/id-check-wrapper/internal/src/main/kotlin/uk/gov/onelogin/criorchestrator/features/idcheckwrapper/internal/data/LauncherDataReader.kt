package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import uk.gov.idcheck.repositories.api.config.BackendMode
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.IdCheckWrapperConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

@Inject
class LauncherDataReader(
    private val sessionStore: SessionStore,
    private val biometricTokenReader: BiometricTokenReader,
    private val configStore: ConfigStore,
) {
    @OptIn(FlowPreview::class)
    suspend fun read(documentVariety: DocumentVariety): LauncherDataReaderResult {
        val sessionId = requireLatestSession().sessionId

        val result = biometricTokenReader.getBiometricToken(sessionId, documentVariety)

        val experimentalComposeNavigation: Boolean =
            configStore.readSingle(IdCheckWrapperConfigKey.ExperimentalComposeNavigation).value

        val nfcAvailability =
            nfcAvailabilityFromConfigValue(
                configStore.readSingle(NfcConfigKey.NfcAvailability).value,
            )

        if (result is BiometricTokenResult.Success) {
            sessionStore.updateToDocumentSelected()
        }
        val session = requireLatestSession()

        val backendMode =
            if (configStore.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
                BackendMode.Bypass
            } else {
                BackendMode.V2
            }
        return when (result) {
            is BiometricTokenResult.Error -> {
                LauncherDataReaderResult.UnrecoverableError(
                    statusCode = result.statusCode,
                    error =
                        DataReaderError(
                            message = "Failed to get biometric token",
                            cause = result.error,
                        ),
                )
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
                        backendMode = backendMode,
                        experimentalComposeNavigation = experimentalComposeNavigation,
                        nfcAvailability = nfcAvailability,
                    ),
                )
            }
        }
    }

    private suspend fun requireLatestSession(): Session = sessionStore.read().first() ?: error("Session doesn't exist")
}

sealed interface LauncherDataReaderResult {
    data class Success(
        val launcherData: LauncherData,
    ) : LauncherDataReaderResult

    data class RecoverableError(
        val error: DataReaderError,
        val statusCode: Int?,
    ) : LauncherDataReaderResult

    data class UnrecoverableError(
        val error: DataReaderError,
        val statusCode: Int?,
    ) : LauncherDataReaderResult
}

data class DataReaderError(
    val message: String,
    val cause: Exception?,
)
