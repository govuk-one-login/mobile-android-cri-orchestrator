package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortdesktopweb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.Analytics

class ConfirmAbortDesktopWebViewModel(
    private val analytics: Analytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ConfirmAbortDesktopWebActions>()
    val actions: SharedFlow<ConfirmAbortDesktopWebActions> = _actions.asSharedFlow()

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortToDesktopWeb,
            title = ConfirmAbortDesktopWebConstants.titleId,
        )
    }

    fun onContinueClicked() {
        analytics.trackButtonEvent(ConfirmAbortDesktopWebConstants.buttonId)

        viewModelScope.launch {
            _actions.emit(ConfirmAbortDesktopWebActions.NavigateToReturnToDesktopWeb)
        }
    }
}
