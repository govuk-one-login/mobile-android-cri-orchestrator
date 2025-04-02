package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

internal class ConfirmPassportViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmPassportAction.NavigateToSyncIdCheck>()
    val action: Flow<ConfirmPassportAction.NavigateToSyncIdCheck> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.ConfirmPassport,
            ConfirmPassportConstants.titleId,
        )
    }

    fun onPrimary() {
        analytics.trackButtonEvent(buttonText = ConfirmPassportConstants.buttonTextId)

        viewModelScope.launch {
            _action.emit(
                ConfirmPassportAction.NavigateToSyncIdCheck,
            )
        }
    }
}
