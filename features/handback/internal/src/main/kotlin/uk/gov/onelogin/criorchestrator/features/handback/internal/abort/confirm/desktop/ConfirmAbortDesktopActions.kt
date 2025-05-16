package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

sealed class ConfirmAbortDesktopActions {
    data object NavigateToReturnToDesktop : ConfirmAbortDesktopActions()
}
