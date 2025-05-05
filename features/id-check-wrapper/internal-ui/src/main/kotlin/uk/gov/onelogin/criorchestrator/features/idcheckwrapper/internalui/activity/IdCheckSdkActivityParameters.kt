package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalui.activity

import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData

internal fun LauncherData.toIdCheckSdkActivityParameters() =
    IdCheckSdkParameters(
        document = this.documentType,
        journey = this.journeyType,
        sessionId = this.sessionId,
        bioToken = this.biometricToken,
    )
