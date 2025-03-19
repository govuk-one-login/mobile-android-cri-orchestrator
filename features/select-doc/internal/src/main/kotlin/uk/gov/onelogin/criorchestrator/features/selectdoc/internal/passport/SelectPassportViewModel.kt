package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

internal class SelectPassportViewModel(
    private val analytics: SelectDocumentAnalytics,
): ViewModel() {
    val titleId = R.string.selectdocument_passport_title

    val options = persistentListOf(
        R.string.selectdocument_passport_selection_yes,
        R.string.selectdocument_passport_selection_no,
    )
    var selectedItem: Int? = null
        private set

    val buttonTextId = R.string.selectdocument_passport_continuebutton

    private fun onScreenStart() {
        analytics.trackScreen(
            SelectDocumentScreenId.SelectPassport,
            titleId
        )
    }

    fun onItemSelected(item: Int) {
        selectedItem = item
    }

    fun onConfirmSelection() {
        analytics.trackButtonEvent(buttonTextId)
    }
}