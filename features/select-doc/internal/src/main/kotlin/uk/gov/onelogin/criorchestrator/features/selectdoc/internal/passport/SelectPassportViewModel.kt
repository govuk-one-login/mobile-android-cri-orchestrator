package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

internal class SelectPassportViewModel(
    private val analytics: SelectDocumentAnalytics,
) : ViewModel() {
    val titleId = R.string.selectdocument_passport_title
    val readMoreButtonTextId = R.string.selectdocument_passport_readmore_button

    val options =
        persistentListOf(
            R.string.selectdocument_passport_selection_yes,
            R.string.selectdocument_passport_selection_no,
        )

    val buttonTextId = R.string.selectdocument_passport_continuebutton

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocumentScreenId.SelectPassport,
            titleId,
        )
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(readMoreButtonTextId)
    }

    fun onConfirmSelection(selectedIndex: Int) {
        analytics.trackFormSubmission(
            buttonText = buttonTextId,
            response = options[selectedIndex],
        )
    }
}
