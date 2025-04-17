package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

class SyncIdCheckViewModel(
    private val configStore: ConfigStore,
    private val launcherDataReader: LauncherDataReader,
    val logger: Logger,
) : ViewModel() {
    private val _state = MutableStateFlow<SyncIdCheckState>(SyncIdCheckState.Loading)
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<SyncIdCheckAction>()
    val actions = _actions.asSharedFlow()

    companion object;

    fun onScreenStart(documentVariety: DocumentVariety) {
        viewModelScope.launch {
            loadLauncher(
                documentVariety = documentVariety,
                enableManualLauncher = configStore.readSingle(IdCheckWrapperConfigKey.EnableManualLauncher).value,
            )
        }
    }

    fun onStubExitStateSelected(selectedExitState: Int) {
        val curState = requireDisplayState()
        require(curState.manualLauncher != null) {
            "Can't select a stub exit state unless the manual launcher is enabled"
        }
        _state.value =
            curState.copy(
                activityResultContractParameters =
                    curState.activityResultContractParameters.copy(
                        stubExitState = ExitStateOption.entries[selectedExitState],
                    ),
                manualLauncher =
                    curState.manualLauncher.copy(
                        selectedExitState = selectedExitState,
                    ),
            )
    }

    fun onIdCheckSdkLaunchRequest(launcherData: LauncherData) =
        viewModelScope.launch {
            _actions.emit(
                SyncIdCheckAction.LaunchIdCheckSdk(
                    launcherData = launcherData,
                    logger = logger,
                ),
            )
        }

    fun onIdCheckSdkResult(exitState: IdCheckSdkExitState) {
        val journeyType = requireDisplayState().launcherData.sessionJourneyType
        val action =
            when (exitState) {
                is IdCheckSdkExitState.Nowhere,
                is IdCheckSdkExitState.ConfirmAnotherWay,
                is IdCheckSdkExitState.ConfirmationAbortedJourney,
                IdCheckSdkExitState.ConfirmationFailed,
                is IdCheckSdkExitState.FaceScanLimitReached,
                IdCheckSdkExitState.UnknownDocumentType,
                ->
                    when (journeyType) {
                        JourneyType.DesktopAppDesktop -> SyncIdCheckAction.NavigateToConfirmAbortToDesktopWeb
                        JourneyType.MobileAppMobile -> SyncIdCheckAction.NavigateToConfirmAbortToMobileWeb
                    }

                IdCheckSdkExitState.HappyPath ->
                    when (journeyType) {
                        JourneyType.DesktopAppDesktop -> SyncIdCheckAction.NavigateToReturnToDesktopWeb
                        JourneyType.MobileAppMobile -> SyncIdCheckAction.NavigateToReturnToMobileWeb
                    }
            }

        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private suspend fun loadLauncher(
        documentVariety: DocumentVariety,
        enableManualLauncher: Boolean,
    ) {
        val launcherData = launcherDataReader.read(documentVariety)
        val manualLauncher =
            if (enableManualLauncher) {
                ManualLauncher(
                    selectedExitState = 0,
                    exitStateOptions = ExitStateOption.displayNames,
                )
            } else {
                null
            }
        _state.value =
            SyncIdCheckState.Display(
                launcherData = launcherData,
                manualLauncher = manualLauncher,
                activityResultContractParameters =
                    IdCheckSdkActivityResultContractParameters(
                        stubExitState = ExitStateOption.None,
                        logger = logger,
                    ),
            )
    }

    private fun requireDisplayState() = _state.value as? SyncIdCheckState.Display ?: error("Expected display state")
}
