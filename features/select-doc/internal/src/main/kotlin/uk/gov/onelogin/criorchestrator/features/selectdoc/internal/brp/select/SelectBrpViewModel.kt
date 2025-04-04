package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

class SelectBrpViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SelectBrpAction>()
    val actions: Flow<SelectBrpAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.SelectBrp,
            SelectBrpConstants.titleId,
        )
    }

    fun onReadMoreClicked() {
        analytics.trackButtonEvent(SelectBrpConstants.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectBrpAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onContinueClicked(selectedItem: Int) {
        analytics.trackFormSubmission(
            buttonText = SelectBrpConstants.continueButtonTextId,
            response = SelectBrpConstants.selectionItems[selectedItem],
        )

        viewModelScope.launch {
            when (selectedItem) {
                0 -> _actions.emit(SelectBrpAction.NavigateToBrpConfirmation)
                1 -> _actions.emit(SelectBrpAction.NavigateToDrivingLicence)
            }
        }
    }
}
