package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.hasAbortedSession
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.isSuccess
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.IdCheckWrapperAnalytics
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.IdCheckWrapperScreenId
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReaderResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType

private const val STUB_BIOMETRIC_TOKEN_DELAY_MS = 2000L

@Suppress("LongParameterList")
class SyncIdCheckViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val configStore: ConfigStore,
    private val sessionStore: SessionStore,
    private val idCheckSdkActiveStateStore: IdCheckSdkActiveStateStore,
    private val launcherDataReader: LauncherDataReader,
    val logger: Logger,
    val analytics: IdCheckWrapperAnalytics,
) : ViewModel() {
    private val _state = MutableStateFlow<SyncIdCheckState>(SyncIdCheckState.Loading)
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<SyncIdCheckAction>(replay = 1)
    val actions = _actions.asSharedFlow()
    var sdkHasDisplayed: Boolean =
        savedStateHandle[SDK_HAS_DISPLAYED] ?: initiallyReturnFalse()

    companion object {
        const val SDK_HAS_DISPLAYED = "uk.gov.onelogin.criorchestrator.sdkHasDisplayed"
    }

    fun onScreenStart(documentVariety: DocumentVariety) {
        analytics.trackScreen(
            IdCheckWrapperScreenId.SyncIdCheckScreen,
            R.string.loading,
        )

        if (!sdkHasDisplayed) {
            if (configStore.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
                viewModelScope.launch {
                    delay(STUB_BIOMETRIC_TOKEN_DELAY_MS)
                    _state.value = SyncIdCheckState.DisplayStubBiometricToken
                }
            } else {
                loadLauncher(documentVariety)
            }
        }
    }

    fun onStubGetBiometricToken(
        documentVariety: DocumentVariety,
        selectedItem: Int,
    ) {
        when (selectedItem) {
            0 -> loadLauncher(documentVariety)
            1 -> {
                viewModelScope.launch {
                    _actions.emit(SyncIdCheckAction.NavigateToRecoverableError)
                }
            }

            2 -> {
                viewModelScope.launch {
                    _actions.emit(SyncIdCheckAction.NavigateToUnrecoverableError)
                }
            }
        }
    }

    private fun loadLauncher(documentVariety: DocumentVariety) {
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

    fun onIdCheckSdkLaunchRequest(launcherData: LauncherData) {
        if (!sdkHasDisplayed) {
            updateSdkHasDisplayed(true)
            idCheckSdkActiveStateStore.setActive()
            viewModelScope.launch {
                _actions.emit(
                    SyncIdCheckAction.LaunchIdCheckSdk(
                        launcherData = launcherData,
                        logger = logger,
                    ),
                )
            }
        }
    }

    fun onIdCheckSdkResult(exitState: IdCheckSdkExitState) {
        idCheckSdkActiveStateStore.setInactive()
        if (exitState.hasAbortedSession()) {
            sessionStore.updateToAborted()
        }
        val journeyType = sessionStore.read().value!!.journeyType
        val action =
            when (exitState.isSuccess()) {
                true ->
                    when (journeyType) {
                        JourneyType.DesktopAppDesktop ->
                            SyncIdCheckAction.NavigateToReturnToDesktopWeb
                        is JourneyType.MobileAppMobile ->
                            SyncIdCheckAction.NavigateToReturnToMobileWeb
                    }
                false ->
                    when (journeyType) {
                        is JourneyType.MobileAppMobile ->
                            SyncIdCheckAction.NavigateToAbortedRedirectToMobileWebHolder(journeyType.redirectUri)
                        JourneyType.DesktopAppDesktop ->
                            SyncIdCheckAction.NavigateToAbortedReturnToDesktopWeb
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
        val launcherDataResult = launcherDataReader.read(documentVariety)
        val manualLauncher =
            if (enableManualLauncher) {
                ManualLauncher(
                    selectedExitState = 0,
                    exitStateOptions = ExitStateOption.displayNames,
                )
            } else {
                null
            }

        when (launcherDataResult) {
            is LauncherDataReaderResult.RecoverableError ->
                _actions.emit(
                    SyncIdCheckAction.NavigateToRecoverableError,
                )

            is LauncherDataReaderResult.UnrecoverableError,
            ->
                _actions.emit(
                    SyncIdCheckAction.NavigateToUnrecoverableError,
                )

            is LauncherDataReaderResult.Success ->
                _state.value =
                    SyncIdCheckState.Display(
                        launcherData = launcherDataResult.launcherData,
                        manualLauncher = manualLauncher,
                        activityResultContractParameters =
                            IdCheckSdkActivityResultContractParameters(
                                stubExitState = ExitStateOption.None,
                                logger = logger,
                            ),
                    )
        }
    }

    private fun requireDisplayState() = _state.value as? SyncIdCheckState.Display ?: error("Expected display state")

    private fun updateSdkHasDisplayed(state: Boolean) {
        sdkHasDisplayed = state
        savedStateHandle[SDK_HAS_DISPLAYED] = state
    }

    private fun initiallyReturnFalse(): Boolean {
        savedStateHandle[SDK_HAS_DISPLAYED] = false
        return false
    }
}
