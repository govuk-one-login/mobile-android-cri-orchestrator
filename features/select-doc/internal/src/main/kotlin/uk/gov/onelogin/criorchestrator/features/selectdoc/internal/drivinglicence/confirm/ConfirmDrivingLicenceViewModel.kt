package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

internal class ConfirmDrivingLicenceViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmDrivingLicenceAction.NavigateToSyncIdCheck>()
    val action: Flow<ConfirmDrivingLicenceAction.NavigateToSyncIdCheck> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.ConfirmDrivingLicence,
            title = ConfirmDrivingLicenceConstants.titleId,
        )
    }

    fun onConfirmClick() {
        analytics.trackButtonEvent(buttonText = ConfirmDrivingLicenceConstants.buttonTextId)

        viewModelScope.launch {
            _action.emit(
                ConfirmDrivingLicenceAction.NavigateToSyncIdCheck,
            )
        }
    }
}
