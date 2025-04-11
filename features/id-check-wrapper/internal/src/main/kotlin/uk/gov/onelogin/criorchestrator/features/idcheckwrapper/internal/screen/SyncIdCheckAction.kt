package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData

sealed interface SyncIdCheckAction {
    data class LaunchIdCheckSdk(
        val launcherData: LauncherData,
        val logger: Logger,
    ) : SyncIdCheckAction

    object NavigateToReturnToMobileWeb : SyncIdCheckAction

    object NavigateToReturnToDesktopWeb : SyncIdCheckAction
}
