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

fun IdCheckSdkExitState.handle(): IdCheckExitStateGroups =
    when (this) {
        IdCheckSdkExitState.HappyPath -> IdCheckExitStateGroups.SUCCESS

        is IdCheckSdkExitState.Nowhere,
        is IdCheckSdkExitState.ConfirmAnotherWay,
        is IdCheckSdkExitState.ConfirmationAbortedJourney,
        IdCheckSdkExitState.UnknownDocumentType,
        -> IdCheckExitStateGroups.ABORT

        IdCheckSdkExitState.ConfirmationFailed,
        is IdCheckSdkExitState.FaceScanLimitReached,
        -> IdCheckExitStateGroups.FACE_SCAN_LIMIT_REACHED
    }
