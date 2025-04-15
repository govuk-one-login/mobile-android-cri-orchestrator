package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import kotlinx.collections.immutable.ImmutableList
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData

sealed interface SyncIdCheckState {
    object Loading : SyncIdCheckState

    data class Display(
        val manualLauncher: ManualLauncher?,
        val launcherData: LauncherData,
        val activityResultContractParameters: IdCheckSdkActivityResultContractParameters,
    ) : SyncIdCheckState
}

data class ManualLauncher(
    val selectedExitState: Int = 0,
    val exitStateOptions: ImmutableList<String> = ExitStateOption.displayNames,
)
