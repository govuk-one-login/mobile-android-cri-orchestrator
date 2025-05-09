package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortdesktop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.Analytics

class ConfirmAbortDesktopViewModel(
    private val analytics: Analytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ConfirmAbortDesktopActions>()
    val actions: SharedFlow<ConfirmAbortDesktopActions> = _actions.asSharedFlow()

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortDesktop,
            title = ConfirmAbortDesktopConstants.titleId,
        )
    }

    fun onContinueClicked() {
        analytics.trackButtonEvent(ConfirmAbortDesktopConstants.buttonId)

        viewModelScope.launch {
            _actions.emit(ConfirmAbortDesktopActions.NavigateToReturnToDesktop)
        }
    }
}
