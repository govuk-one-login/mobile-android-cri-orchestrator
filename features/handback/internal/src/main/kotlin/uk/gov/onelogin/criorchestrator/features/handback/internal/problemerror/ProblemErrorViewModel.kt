package uk.gov.onelogin.criorchestrator.features.handback.internal.problemerror

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

internal class ProblemErrorViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ProblemErrorAction>()
    val actions: Flow<ProblemErrorAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ProblemError,
            title = ProblemErrorConstants.titleId,
        )
    }

    fun onButtonClick() {
        analytics.trackButtonEvent(
            buttonText = ProblemErrorConstants.buttonTextId,
        )

        viewModelScope.launch {
            _actions.emit(ProblemErrorAction.NavigateToAbort)
        }
    }
}
