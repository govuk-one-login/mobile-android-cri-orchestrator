package uk.gov.onelogin.criorchestrator.features.handback.internal.problemerror

sealed class ProblemErrorAction {
    data object NavigateToAbort : ProblemErrorAction()
}
