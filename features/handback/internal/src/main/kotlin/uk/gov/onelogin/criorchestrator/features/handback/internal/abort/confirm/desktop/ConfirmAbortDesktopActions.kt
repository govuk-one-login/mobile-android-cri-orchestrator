package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

sealed interface ConfirmAbortDesktopActions {
    data object NavigateToReturnToDesktop : ConfirmAbortDesktopActions

    data object NavigateToUnrecoverableError : ConfirmAbortDesktopActions

    data object NavigateToOfflineError : ConfirmAbortDesktopActions
}
