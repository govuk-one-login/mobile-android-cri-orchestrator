package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

sealed class RecoverableErrorAction {
    data object NavigateBack : RecoverableErrorAction()
}
