package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class ReturnToDesktopWebViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ReturnToDesktopWebAction>()
    val actions: SharedFlow<ReturnToDesktopWebAction> = _actions.asSharedFlow()

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ReturnToDesktopWeb,
            title = ReturnToDesktopWebConstants.titleId,
        )

        viewModelScope.launch {
            delay(2_000)
            _actions.emit(ReturnToDesktopWebAction.RequestReview)
        }
    }
}
