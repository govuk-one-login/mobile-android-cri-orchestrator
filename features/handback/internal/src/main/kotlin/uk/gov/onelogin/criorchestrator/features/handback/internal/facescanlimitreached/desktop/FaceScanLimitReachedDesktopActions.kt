package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

sealed interface FaceScanLimitReachedDesktopActions {
    data object NavigateToReturnToDesktop : FaceScanLimitReachedDesktopActions

    data object NavigateToUnrecoverableError : FaceScanLimitReachedDesktopActions

    data object NavigateToOfflineError : FaceScanLimitReachedDesktopActions
}
