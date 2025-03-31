package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

internal class SelectPassportViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            SelectPassportState(
                options =
                    persistentListOf(
                        R.string.selectdocument_passport_selection_yes,
                        R.string.selectdocument_passport_selection_no,
                    ),
            ),
        )
    val state: StateFlow<SelectPassportState> = _state

    private val _actions = MutableSharedFlow<SelectPassportAction>()
    val actions: Flow<SelectPassportAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocumentScreenId.SelectPassport,
            title = SelectPassportConstants.titleId,
        )
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(buttonText = SelectPassportConstants.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectPassportAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onConfirmSelection(selectedIndex: Int) {
        analytics.trackFormSubmission(
            buttonText = SelectPassportConstants.buttonTextId,
            response = state.value.options[selectedIndex],
        )

        viewModelScope.launch {
            when (selectedIndex) {
                0 -> _actions.emit(SelectPassportAction.NavigateToConfirmation)
                1 -> _actions.emit(SelectPassportAction.NavigateToBrp)
            }
        }
    }
}
