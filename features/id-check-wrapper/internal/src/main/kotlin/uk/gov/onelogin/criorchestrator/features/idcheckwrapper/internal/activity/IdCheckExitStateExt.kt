package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import uk.gov.idcheck.sdk.IdCheckSdkExitState

fun IdCheckSdkExitState.hasAbortedSession(): Boolean =
    when (this) {
        is IdCheckSdkExitState.Nowhere,
        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.ConfirmationFailed,
        IdCheckSdkExitState.UnknownDocumentType,
        -> true

        is IdCheckSdkExitState.FaceScanLimitReached,
        IdCheckSdkExitState.HappyPath,
        IdCheckSdkExitState.NoValidSessionError,
        -> false
    }

fun IdCheckSdkExitState.isSuccess(): Boolean =
    when (this) {
        IdCheckSdkExitState.HappyPath,
        -> true

        is IdCheckSdkExitState.Nowhere,
        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.ConfirmationFailed,
        IdCheckSdkExitState.UnknownDocumentType,
        is IdCheckSdkExitState.FaceScanLimitReached,
        IdCheckSdkExitState.NoValidSessionError,
        -> false
    }

fun IdCheckSdkExitState.isLimitReached(): Boolean =
    when (this) {
        is IdCheckSdkExitState.FaceScanLimitReached -> true

        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.ConfirmationFailed,
        IdCheckSdkExitState.HappyPath,
        IdCheckSdkExitState.Nowhere,
        IdCheckSdkExitState.UnknownDocumentType,
        IdCheckSdkExitState.NoValidSessionError,
        -> false
    }

fun IdCheckSdkExitState.noValidSession(): Boolean =
    when (this) {
        IdCheckSdkExitState.NoValidSessionError -> true

        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.ConfirmationFailed,
        IdCheckSdkExitState.HappyPath,
        IdCheckSdkExitState.Nowhere,
        IdCheckSdkExitState.UnknownDocumentType,
        is IdCheckSdkExitState.FaceScanLimitReached,
        -> false
    }
