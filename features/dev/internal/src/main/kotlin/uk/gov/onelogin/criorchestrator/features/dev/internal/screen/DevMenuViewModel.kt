package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config

internal class DevMenuViewModel(
    private val configStore: ConfigStore,
) : ViewModel() {
    private val _state =
        MutableStateFlow<DevMenuUiState>(
            DevMenuUiState(
                config = Config(),
            ),
        )
    val state: StateFlow<DevMenuUiState> = _state

    init {
        viewModelScope.launch {
            configStore.readAll().collect { config ->
                _state.value = _state.value.copy(config = config)
            }
        }
    }

    fun onEntryChange(entry: Config.Entry<Config.Value>) = configStore.write(entry)
}
