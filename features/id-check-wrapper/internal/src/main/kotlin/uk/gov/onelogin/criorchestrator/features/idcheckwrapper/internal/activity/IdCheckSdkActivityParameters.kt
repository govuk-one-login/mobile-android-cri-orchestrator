package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import uk.gov.idcheck.repositories.api.webhandover.backend.BackendMode
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData

internal fun LauncherData.toIdCheckSdkActivityParameters() =
    IdCheckSdkParameters(
        document = this.documentType,
        journey = this.journeyType,
        sessionId = this.sessionId,
        bioToken = this.biometricToken,
        backendMode = BackendMode.V2,
    )
