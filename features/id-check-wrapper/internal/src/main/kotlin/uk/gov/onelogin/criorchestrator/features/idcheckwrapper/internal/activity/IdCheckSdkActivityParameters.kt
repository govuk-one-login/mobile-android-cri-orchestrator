package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.idcheck.repositories.api.config.Config as IdCheckSdkConfig

internal fun LauncherData.toIdCheckSdkActivityParameters() =
    IdCheckSdkParameters(
        document = this.documentType,
        journey = this.journeyType,
        sessionId = this.sessionId,
        bioToken = this.biometricToken,
        config =
            IdCheckSdkConfig(
                backendMode = this.backendMode,
                experimentalComposeNavigation = this.experimentalComposeNavigation,
                nfcAvailability = this.nfcAvailability,
            ),
    )
