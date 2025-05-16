package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

sealed interface ConfirmAbortMobileAction {
    data class ContinueGovUk(
        val redirectUri: String,
    ) : ConfirmAbortMobileAction

    data object NavigateToUnrecoverableError : ConfirmAbortMobileAction

    data object NavigateToOfflineError : ConfirmAbortMobileAction
}
