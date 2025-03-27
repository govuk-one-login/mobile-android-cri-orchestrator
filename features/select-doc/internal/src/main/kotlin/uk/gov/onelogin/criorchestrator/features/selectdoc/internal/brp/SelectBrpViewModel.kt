package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectBrpAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectBrpScreenId

class SelectBrpViewModel(
    private val analytics: SelectBrpAnalytics,
) : ViewModel() {
    private val _state = MutableStateFlow(SelectBrpState())
    val state: StateFlow<SelectBrpState> = _state
    private val _actions = MutableSharedFlow<SelectBrpAction>()
    val actions: Flow<SelectBrpAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            SelectBrpScreenId.SelectBrp,
            state.value.titleId,
        )
    }

    fun onReadMoreClicked() {
        analytics.trackButtonEvent(state.value.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectBrpAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onContinueClicked(selectedItem: Int) {
        analytics.trackFormSubmission(
            buttonText = state.value.buttonTextId,
            response = state.value.options[selectedItem],
        )

        viewModelScope.launch {
            when (selectedItem) {
                0 -> _actions.emit(SelectBrpAction.NavigateToBrpConfirmation)
                1 -> _actions.emit(SelectBrpAction.NavigateToDrivingLicence)
            }
        }
    }
}

sealed class SelectBrpAction {
    data object NavigateToBrpConfirmation : SelectBrpAction()

    data object NavigateToDrivingLicence : SelectBrpAction()

    data object NavigateToTypesOfPhotoID : SelectBrpAction()
}

data class SelectBrpState(
    val titleId: Int = R.string.selectdocument_brp_title,
    val readMoreButtonTextId: Int = R.string.selectdocument_brp_read_more_button,
    val options: List<Int> =
        persistentListOf(
            R.string.selectdocument_brp_selection_yes,
            R.string.selectdocument_brp_selection_no,
        ),
    val buttonTextId: Int = R.string.selectdocument_brp_continue_button,
)
