package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

sealed interface UnrecoverableErrorAction {
    data object NavigateToConfirmAbortMobile : UnrecoverableErrorAction

    data object NavigateToConfirmAbortDesktop : UnrecoverableErrorAction
}
