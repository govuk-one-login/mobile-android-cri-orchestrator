package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.passport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.ConfirmDocumentAction
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.analytics.ConfirmDocumentScreenId

internal class ConfirmPassportViewModel(
    private val analytics: SelectDocAnalytics
) : ViewModel() {
    internal val titleId = R.string.confirmdocument_passport_title
    internal val buttonTextId = R.string.confirmdocument_confirmbutton

    private val _actions = MutableSharedFlow<ConfirmDocumentAction>()
    val actions: Flow<ConfirmDocumentAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            ConfirmDocumentScreenId.ConfirmPassport,
            titleId,
        )
    }

    fun onPrimary() {
        analytics.trackButtonEvent(buttonText = buttonTextId)

        viewModelScope.launch {
            _actions.emit(ConfirmDocumentAction.NavigateToDocumentPhotoScanner)
        }
    }
}