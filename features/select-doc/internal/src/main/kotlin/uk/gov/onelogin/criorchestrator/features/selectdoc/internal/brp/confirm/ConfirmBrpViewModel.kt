package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

internal class ConfirmBrpViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmBrpAction.NavigateToSyncIdCheck>()
    val action: Flow<ConfirmBrpAction.NavigateToSyncIdCheck> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.ConfirmBrp,
            ConfirmBrpConstants.titleId,
        )
    }

    fun onPrimary() {
        analytics.trackButtonEvent(buttonText = ConfirmBrpConstants.buttonTextId)

        viewModelScope.launch {
            _action.emit(
                ConfirmBrpAction.NavigateToSyncIdCheck,
            )
        }
    }
}
