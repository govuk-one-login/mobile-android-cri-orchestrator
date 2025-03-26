package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.passport

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.analytics.ConfirmDocumentScreenId

internal class ConfirmPassportViewModel(
    private val analytics: SelectDocAnalytics
) : ViewModel() {
    private val title = R.string.confirmdocument_passport_title
    fun onScreenStart() {
        analytics.trackScreen(
            ConfirmDocumentScreenId.ConfirmPassport,
            title,
        )
    }
}