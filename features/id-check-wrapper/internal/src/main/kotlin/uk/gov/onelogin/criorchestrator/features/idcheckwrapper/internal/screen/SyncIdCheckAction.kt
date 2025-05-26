package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData

sealed interface SyncIdCheckAction {
    data class LaunchIdCheckSdk(
        val launcherData: LauncherData,
        val logger: Logger,
    ) : SyncIdCheckAction

    data object NavigateToReturnToMobileWeb : SyncIdCheckAction

    data object NavigateToReturnToDesktopWeb : SyncIdCheckAction

    data object NavigateToRecoverableError : SyncIdCheckAction

    data object NavigateToUnrecoverableError : SyncIdCheckAction

    data class NavigateToAbortedRedirectToMobileWebHolder(
        val redirectUri: String,
    ) : SyncIdCheckAction

    data object NavigateToAbortedReturnToDesktopWeb : SyncIdCheckAction

    data object NavigateToFaceScanLimitReachedDesktop : SyncIdCheckAction

    data object NavigateToFaceScanLimitReachedMobile : SyncIdCheckAction
}
