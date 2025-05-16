package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import uk.gov.idcheck.sdk.IdCheckSdkExitState

fun IdCheckSdkExitState.hasAbortedSession(): Boolean =
    when (this) {
        is IdCheckSdkExitState.Nowhere,
        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.ConfirmationFailed,
        is IdCheckSdkExitState.FaceScanLimitReached,
        IdCheckSdkExitState.UnknownDocumentType,
        -> true

        IdCheckSdkExitState.HappyPath -> false
    }

fun IdCheckSdkExitState.isSuccess(): Boolean =
    when (this) {
        IdCheckSdkExitState.HappyPath -> true

        is IdCheckSdkExitState.Nowhere,
        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.ConfirmationFailed,
        is IdCheckSdkExitState.FaceScanLimitReached,
        IdCheckSdkExitState.UnknownDocumentType,
        -> false
    }
