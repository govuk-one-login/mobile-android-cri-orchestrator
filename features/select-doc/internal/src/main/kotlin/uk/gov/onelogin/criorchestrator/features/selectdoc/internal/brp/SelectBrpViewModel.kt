package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectBrpScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics

class SelectBrpViewModel(
    private val analytics: SelectDocumentAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SelectBrpAction>()
    val actions: Flow<SelectBrpAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            SelectBrpScreenId.SelectBrp,
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
