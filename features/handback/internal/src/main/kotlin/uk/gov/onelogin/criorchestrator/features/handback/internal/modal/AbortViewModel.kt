package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionAbortedOrUnavailable

class AbortViewModel(
    private val isSessionAbortedOrUnavailable: IsSessionAbortedOrUnavailable,
) : ViewModel() {
    private lateinit var _isAborted: Flow<Boolean>
    lateinit var isAborted: Flow<Boolean>

    init {
        viewModelScope.launch {
            _isAborted = isSessionAbortedOrUnavailable()
            isAborted = _isAborted
        }
    }
}
