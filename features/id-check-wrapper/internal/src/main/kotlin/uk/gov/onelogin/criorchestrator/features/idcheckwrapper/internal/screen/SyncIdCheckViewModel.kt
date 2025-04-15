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
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.SyncIdCheckAnalytics
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.SyncIdCheckScreenId
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReaderResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

class SyncIdCheckViewModel(
    private val launcherDataReader: LauncherDataReader,
    val logger: Logger,
    val analytics: SyncIdCheckAnalytics,
) : ViewModel() {
    private val _state = MutableStateFlow<SyncIdCheckState>(SyncIdCheckState.Loading)
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<SyncIdCheckAction>()
    val actions = _actions.asSharedFlow()

    fun onScreenStart(documentVariety: DocumentVariety) {
        analytics.trackScreen(
            SyncIdCheckScreenId.SyncIdCheckScreen,
            R.string.loading,
        )

        viewModelScope.launch {
            loadManualLauncher(documentVariety)
        }
    }

    fun onStubExitStateSelected(selectedExitState: Int) {
        val curState = requireDisplayState()
        _state.value =
            curState.copy(
                activityResultContractParameters =
                    curState.activityResultContractParameters.copy(
                        stubExitState = ExitStateOption.entries[selectedExitState],
                    ),
                manualLauncher =
                    curState.manualLauncher?.copy(
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

    fun onIdCheckSdkResult(exitState: IdCheckSdkExitState) =
        viewModelScope.launch {
            when (exitState) {
                is IdCheckSdkExitState.Nowhere,
                is IdCheckSdkExitState.ConfirmAnotherWay,
                is IdCheckSdkExitState.ConfirmationAbortedJourney,
                IdCheckSdkExitState.ConfirmationFailed,
                is IdCheckSdkExitState.FaceScanLimitReached,
                IdCheckSdkExitState.UnknownDocumentType,
                -> exitStateNotHandled()

                IdCheckSdkExitState.HappyPath ->
                    _actions.emit(
                        when (requireDisplayState().launcherData.sessionJourneyType) {
                            JourneyType.DesktopAppDesktop -> SyncIdCheckAction.NavigateToReturnToDesktopWeb
                            JourneyType.MobileAppMobile -> SyncIdCheckAction.NavigateToReturnToMobileWeb
                        },
                    )
            }
        }

    private suspend fun loadManualLauncher(documentVariety: DocumentVariety) {
        val launcherDataResult = launcherDataReader.read(documentVariety)

        when (launcherDataResult) {
            is LauncherDataReaderResult.RecoverableError ->
                _actions.emit(
                    SyncIdCheckAction.NavigateToRecoverableError,
                )

            null,
            is LauncherDataReaderResult.UnRecoverableError,
            ->
                _actions.emit(
                    SyncIdCheckAction.NavigateToUnRecoverableError,
                )

            is LauncherDataReaderResult.Success ->
                _state.value =
                    SyncIdCheckState.Display(
                        launcherData = launcherDataResult.launcherData,
                        manualLauncher =
                            ManualLauncher(
                                selectedExitState = 0,
                                exitStateOptions = ExitStateOption.displayNames,
                            ),
                        activityResultContractParameters =
                            IdCheckSdkActivityResultContractParameters(
                                stubExitState = ExitStateOption.None,
                                logger = logger,
                            ),
                    )
        }
    }

    private fun requireDisplayState() = _state.value as? SyncIdCheckState.Display ?: error("Expected display state")

    private fun exitStateNotHandled(): Nothing = error("Not yet implemented (DCMAW-11490)")
}
