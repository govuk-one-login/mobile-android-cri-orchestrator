package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReaderResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

class SyncIdCheckViewModel(
    private val launcherDataReader: LauncherDataReader,
) : ViewModel() {
    private val _state = MutableStateFlow<SyncIdCheckState>(SyncIdCheckState.Loading)
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<SyncIdCheckAction>()
    val actions = _actions.asSharedFlow()

    fun onScreenStart(documentVariety: DocumentVariety) {
        viewModelScope.launch {
            loadManualLauncher(documentVariety)
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
            is LauncherDataReaderResult.UnRecoverableError ->
                _actions.emit(
                    SyncIdCheckAction.NavigateToUnRecoverableError,
                )

            is LauncherDataReaderResult.Success ->
                _state.value =
                    SyncIdCheckState.DisplayManualLauncher(
                        launcherData = launcherDataResult.launcherData,
                    )
        }
    }
}
