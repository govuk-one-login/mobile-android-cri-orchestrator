package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionAbortedOrUnavailable
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(AbortModalViewModel::class)
class AbortModalViewModel(
    private val isSessionAbortedOrUnavailable: IsSessionAbortedOrUnavailable,
) : ViewModel() {
    private val _isAborted = MutableStateFlow<Boolean>(false)
    val isAborted: Flow<Boolean> = _isAborted.asStateFlow()

    init {
        viewModelScope.launch {
            isSessionAbortedOrUnavailable().collect { value ->
                _isAborted.value = value
            }
        }
    }
}
