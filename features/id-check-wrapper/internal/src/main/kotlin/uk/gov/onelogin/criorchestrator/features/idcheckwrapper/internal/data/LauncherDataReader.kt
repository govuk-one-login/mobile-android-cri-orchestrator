package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import android.util.Log
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
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
                    LauncherDataReaderResult.UnRecoverableError(result.error)
                }

                // The network library is currently not returning this status
                BiometricTokenResult.Loading -> {
                    Log.d("LauncherDataReader", "Loading")
                    null
                }

                // The network library is currently not returning this status
                BiometricTokenResult.Offline -> {
                    LauncherDataReaderResult.RecoverableError(Exception("Device is offline"))
                }

                is BiometricTokenResult.Success -> {
                    LauncherDataReaderResult.Success(
                        LauncherData(
                            sessionId = session.sessionId,
                            biometricToken = result.token,
                            journeyType = session.journeyType,
                            documentType = documentVariety.toDocumentType(),
                        ),
                    )
                }
            }
        }

        private val Session.journeyType: JourneyType
            get() =
                if (this.redirectUri == null) {
                    JourneyType.DESKTOP_APP_DESKTOP
                } else {
                    JourneyType.MOBILE_APP_MOBILE
                }
    }

sealed interface LauncherDataReaderResult {
    data class Success(
        val launcherData: LauncherData,
    ) : LauncherDataReaderResult

    data class RecoverableError(
        val exception: Exception,
    ) : LauncherDataReaderResult

    data class UnRecoverableError(
        val exception: Exception,
    ) : LauncherDataReaderResult
}
