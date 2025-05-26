package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import uk.gov.idcheck.sdk.IdCheckSdkExitState

fun IdCheckSdkExitState.hasAbortedSession(): Boolean =
    when (this) {
        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        -> true

        is IdCheckSdkExitState.Nowhere,
        IdCheckSdkExitState.ConfirmationFailed,
        is IdCheckSdkExitState.FaceScanLimitReached,
        IdCheckSdkExitState.UnknownDocumentType,
        IdCheckSdkExitState.HappyPath -> false
    }

fun IdCheckSdkExitState.handle(): IdCheckExitStateGroups =
    when (this) {
        IdCheckSdkExitState.HappyPath -> IdCheckExitStateGroups.SUCCESS

        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        -> IdCheckExitStateGroups.ABORT

        is IdCheckSdkExitState.Nowhere,
        IdCheckSdkExitState.UnknownDocumentType,
        IdCheckSdkExitState.ConfirmationFailed,
        is IdCheckSdkExitState.FaceScanLimitReached,
        -> IdCheckExitStateGroups.FACE_SCAN_LIMIT_REACHED
    }
