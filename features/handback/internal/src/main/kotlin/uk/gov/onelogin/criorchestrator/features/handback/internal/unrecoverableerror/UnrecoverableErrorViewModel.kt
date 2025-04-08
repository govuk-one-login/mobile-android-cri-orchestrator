package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

internal class UnrecoverableErrorViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<UnrecoverableErrorAction>()
    val actions: Flow<UnrecoverableErrorAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.UnrecoverableError,
            title = UnrecoverableErrorConstants.titleId,
        )
    }

    fun onButtonClick() {
        analytics.trackButtonEvent(
            buttonText = UnrecoverableErrorConstants.buttonTextId,
        )

        viewModelScope.launch {
            _actions.emit(UnrecoverableErrorAction.NavigateToAbort)
        }
    }
}
