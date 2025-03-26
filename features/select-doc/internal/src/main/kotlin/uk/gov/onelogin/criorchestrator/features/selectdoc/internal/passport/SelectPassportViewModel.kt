package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

internal class SelectPassportViewModel(
    private val analytics: SelectDocumentAnalytics,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            SelectPassportState(
                titleId = R.string.selectdocument_passport_title,
                readMoreButtonTextId = R.string.selectdocument_passport_readmore_button,
                options =
                    persistentListOf(
                        R.string.selectdocument_passport_selection_yes,
                        R.string.selectdocument_passport_selection_no,
                    ),
                buttonTextId = R.string.selectdocument_passport_continuebutton,
            ),
        )
    val state: StateFlow<SelectPassportState> = _state

    private val _actions = MutableSharedFlow<SelectPassportAction>()
    val actions: Flow<SelectPassportAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocumentScreenId.SelectPassport,
            state.value.titleId,
        )
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(state.value.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectPassportAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onConfirmSelection(selectedIndex: Int) {
        analytics.trackFormSubmission(
            buttonText = state.value.buttonTextId,
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
