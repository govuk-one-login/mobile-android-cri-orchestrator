package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.ConfirmDocumentScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics

internal class ConfirmPassportViewModel(
    private val analytics: SelectDocAnalytics
) : ViewModel() {
    private val _actions = MutableSharedFlow<ConfirmPassportAction>()
    val actions: Flow<ConfirmPassportAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            ConfirmDocumentScreenId.ConfirmPassport,
            ConfirmPassportConstants.titleId,
        )
    }

    fun onPrimary() {
        analytics.trackButtonEvent(buttonText = ConfirmPassportConstants.buttonTextId)

        viewModelScope.launch {
            _actions.emit(ConfirmPassportAction.NavigateToPassportPhotoScanner)
        }
    }
}