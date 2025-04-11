package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData

sealed interface SyncIdCheckState {
    object Loading : SyncIdCheckState

    data class DisplayManualLauncher(
        val launcherData: LauncherData,
    ) : SyncIdCheckState

    object Display : SyncIdCheckState
}
