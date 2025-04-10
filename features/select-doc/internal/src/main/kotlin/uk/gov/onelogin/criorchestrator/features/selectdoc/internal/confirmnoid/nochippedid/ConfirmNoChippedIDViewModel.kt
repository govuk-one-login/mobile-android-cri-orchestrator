package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

class ConfirmNoChippedIDViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ConfirmNoChippedIDAction>()
    val actions: Flow<ConfirmNoChippedIDAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.ConfirmNoChippedID,
            ConfirmNoChippedIDConstants.titleId,
        )
    }

    fun onConfirmClick() {
        analytics.trackButtonEvent(ConfirmNoChippedIDConstants.confirmButtonTextId)
        viewModelScope.launch {
            _actions.emit(ConfirmNoChippedIDAction.NavigateToConfirmAbort)
        }
    }
}