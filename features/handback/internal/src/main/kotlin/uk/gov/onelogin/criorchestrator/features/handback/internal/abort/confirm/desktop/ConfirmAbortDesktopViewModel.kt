package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortDisplayState
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.libraries.analytics.Analytics

class ConfirmAbortDesktopViewModel(
    private val analytics: Analytics,
    private val abortSession: AbortSession,
) : ViewModel() {
    private val _displayState = MutableStateFlow<ConfirmAbortDisplayState>(ConfirmAbortDisplayState.Display)
    val displayState = _displayState.asStateFlow()
    private val _actions = MutableSharedFlow<ConfirmAbortDesktopActions>()
    val actions: SharedFlow<ConfirmAbortDesktopActions> = _actions.asSharedFlow()

    fun onScreenStart() {
        _displayState.value = ConfirmAbortDisplayState.Display
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortDesktop,
            title = ConfirmAbortDesktopConstants.titleId,
        )
    }

    fun onContinueClicked() {
        _displayState.value = ConfirmAbortDisplayState.Loading
        analytics.trackButtonEvent(ConfirmAbortDesktopConstants.buttonId)

        viewModelScope.launch {
            when (abortSession()) {
                AbortSession.Result.Error.Offline ->
                    _actions.emit(ConfirmAbortDesktopActions.NavigateToOfflineError)
                is AbortSession.Result.Error.Unrecoverable ->
                    _actions.emit(ConfirmAbortDesktopActions.NavigateToUnrecoverableError)
                AbortSession.Result.Success -> {
                    _actions.emit(ConfirmAbortDesktopActions.NavigateToReturnToDesktop)
                }
            }
        }
    }
}
