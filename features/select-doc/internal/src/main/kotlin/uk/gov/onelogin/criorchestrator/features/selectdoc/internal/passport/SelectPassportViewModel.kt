package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

internal class SelectPassportViewModel(
    private val analytics: SelectDocumentAnalytics,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            SelectPassportState(
                selection = PassportSelection.Unselected,
            ),
        )
    val state: StateFlow<SelectPassportState> = _state

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocumentScreenId.SelectPassport,
            state.value.titleId,
        )
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(state.value.readMoreButtonTextId)
    }

    fun onConfirmSelection(selectedIndex: Int) {
        analytics.trackFormSubmission(
            buttonText = state.value.buttonTextId,
            response = state.value.options[selectedIndex],
        )

        when (selectedIndex) {
            0 -> _state.value = _state.value.copy(selection = PassportSelection.Selected)
            1 -> _state.value = _state.value.copy(selection = PassportSelection.NotSelected)
        }
    }
}
