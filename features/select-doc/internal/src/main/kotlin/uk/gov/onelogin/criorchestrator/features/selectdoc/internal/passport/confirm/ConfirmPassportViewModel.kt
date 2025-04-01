package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.ConfirmDocumentScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics

internal class ConfirmPassportViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _actionDetails = MutableSharedFlow<DocumentType>()
    val actionDetails: Flow<DocumentType> = _actionDetails

    fun onScreenStart() {
        analytics.trackScreen(
            ConfirmDocumentScreenId.ConfirmPassport,
            ConfirmPassportConstants.titleId,
        )
    }

    fun onPrimary() {
        analytics.trackButtonEvent(buttonText = ConfirmPassportConstants.buttonTextId)

        viewModelScope.launch {
            _actionDetails.emit(
                DocumentType.NFC_PASSPORT,
            )
        }
    }
}
