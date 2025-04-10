package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
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
    ) {
        @OptIn(FlowPreview::class)
        suspend fun read(documentVariety: DocumentVariety): LauncherData {
            val session =
                sessionStore
                    .read()
                    .filterNotNull()
                    // The session store should return very quickly as it's stored locally.
                    // If there is a timeout, then the session presumably lost due to process death.
                    .timeout(10.seconds)
                    .first()

            val biometricToken =
                BiometricToken(
                    accessToken = "TODO",
                    opaqueId = "TODO",
                )

            return LauncherData(
                sessionId = session.sessionId,
                journeyType = session.journeyType,
                biometricToken = biometricToken,
                documentType = documentVariety.toDocumentType(),
            )
        }

        private val Session.journeyType: JourneyType
            get() =
                if (this.redirectUri == null) {
                    JourneyType.DESKTOP_APP_DESKTOP
                } else {
                    JourneyType.MOBILE_APP_MOBILE
                }
    }
