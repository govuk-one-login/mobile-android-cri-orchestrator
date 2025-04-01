package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

class TypesOfPhotoIDViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocumentScreenId.TypesOfPhotoID,
            title = TypesOfPhotoIDConstants.titleId,
        )
    }
}
