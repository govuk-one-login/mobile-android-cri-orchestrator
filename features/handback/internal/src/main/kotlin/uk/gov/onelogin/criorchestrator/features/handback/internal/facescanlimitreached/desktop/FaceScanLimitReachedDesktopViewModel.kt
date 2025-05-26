package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.libraries.analytics.Analytics

class FaceScanLimitReachedDesktopViewModel(
    private val analytics: Analytics,
    private val abortSession: AbortSession,
) : ViewModel() {
    private val _state = MutableStateFlow<ConfirmAbortState>(ConfirmAbortState.Display)
    val state = _state.asStateFlow()
    private val _actions = MutableSharedFlow<FaceScanLimitReachedDesktopActions>()
    val actions: SharedFlow<FaceScanLimitReachedDesktopActions> = _actions.asSharedFlow()

    fun onScreenStart() {
        _state.value = ConfirmAbortState.Display
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortDesktop,
            title = FaceScanLimitReachedDesktopConstants.titleId,
        )
    }

    fun onButtonClicked() {
        _state.value = ConfirmAbortState.Loading
        analytics.trackButtonEvent(FaceScanLimitReachedDesktopConstants.buttonId)

        viewModelScope.launch {
            when (abortSession()) {
                AbortSession.Result.Error.Offline ->
                    _actions.emit(FaceScanLimitReachedDesktopActions.NavigateToOfflineError)
                is AbortSession.Result.Error.Unrecoverable ->
                    _actions.emit(FaceScanLimitReachedDesktopActions.NavigateToUnrecoverableError)
                AbortSession.Result.Success -> {
                    _actions.emit(FaceScanLimitReachedDesktopActions.NavigateToReturnToDesktop)
                }
            }
        }
    }
}
