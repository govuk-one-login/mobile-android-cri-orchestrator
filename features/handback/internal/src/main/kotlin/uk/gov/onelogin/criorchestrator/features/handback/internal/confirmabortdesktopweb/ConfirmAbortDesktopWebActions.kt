package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortdesktopweb

sealed class ConfirmAbortDesktopWebActions {
    data object NavigateToReturnToDesktopWeb : ConfirmAbortDesktopWebActions()
}
