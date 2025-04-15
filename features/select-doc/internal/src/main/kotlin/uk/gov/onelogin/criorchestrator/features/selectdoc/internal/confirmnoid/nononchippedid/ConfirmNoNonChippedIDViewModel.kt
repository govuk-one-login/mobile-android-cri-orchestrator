package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

class ConfirmNoNonChippedIDViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmNoNonChippedIDAction>()
    val action: Flow<ConfirmNoNonChippedIDAction> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.ConfirmNoNonChippedID,
            ConfirmNoNonChippedIDConstants.titleId,
        )
    }

    fun onConfirmClick() {
        analytics.trackButtonEvent(ConfirmNoNonChippedIDConstants.confirmButtonTextId)
        viewModelScope.launch {
            _action.emit(ConfirmNoNonChippedIDAction.NavigateToConfirmAbort)
        }
    }
}
