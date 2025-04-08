package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

sealed class UnrecoverableErrorAction {
    data object NavigateToAbort : UnrecoverableErrorAction()
}
