package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.desktop

sealed interface UnableToConfirmIdentityDesktopActions {
    data object NavigateToReturnToDesktop : UnableToConfirmIdentityDesktopActions

    data object NavigateToUnrecoverableError : UnableToConfirmIdentityDesktopActions

    data object NavigateToOfflineError : UnableToConfirmIdentityDesktopActions
}
