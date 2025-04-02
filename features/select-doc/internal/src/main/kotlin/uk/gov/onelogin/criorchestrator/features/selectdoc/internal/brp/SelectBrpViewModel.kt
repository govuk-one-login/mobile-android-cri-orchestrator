package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

class SelectBrpViewModel(
    private val analytics: SelectDocumentAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SelectBrpAction>()
    val actions: Flow<SelectBrpAction> = _actions
    private val _state = MutableStateFlow(SelectBrpState())
    val state: StateFlow<SelectBrpState> = _state

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocumentScreenId.SelectBrp,
            SelectBrpConstants.titleId,
        )
    }

    fun onReadMoreClicked() {
        analytics.trackButtonEvent(SelectBrpConstants.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectBrpAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onItemSelected(selectedItem: Int) {
        _state.update {
            it.copy(
                selectedItem = selectedItem,
                continueButtonEnabled = true,
            )
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
