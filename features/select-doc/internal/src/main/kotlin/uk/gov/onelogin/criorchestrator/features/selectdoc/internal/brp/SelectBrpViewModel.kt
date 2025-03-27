package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            R.string.selectdocument_brp_title
        )
    }

    fun onReadMoreClicked() {
        viewModelScope.launch {
            _actions.emit(SelectBrpAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onItemSelected(selectedIndex: Int) {
        _state.value = _state.value.copy(idTypeSelected = selectedIndex)
    }

    fun onContinueClick() {
        viewModelScope.launch {
            _actions.emit(SelectBrpAction.NavigateToConfirmation)
        }
    }
}

sealed class SelectBrpAction {
    data object NavigateToConfirmation : SelectBrpAction()
    data object NavigateToTypesOfPhotoID : SelectBrpAction()
}

data class SelectBrpState(
    val idTypeSelected: Int? = null
)
