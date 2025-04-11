package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

class SyncIdCheckViewModel(
    private val launcherDataReader: LauncherDataReader,
) : ViewModel() {
    private val _state = MutableStateFlow<SyncIdCheckState>(SyncIdCheckState.Loading)
    val state = _state.asStateFlow()

    fun onScreenStart(documentVariety: DocumentVariety) {
        viewModelScope.launch {
            loadManualLauncher(documentVariety)
        }
    }

    private suspend fun loadManualLauncher(documentVariety: DocumentVariety) {
        val launcherData = launcherDataReader.read(documentVariety)
        _state.value =
            SyncIdCheckState.DisplayManualLauncher(
                launcherData = launcherData,
            )
    }
}
