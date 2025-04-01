package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId

class TypesOfPhotoIDViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.TypesOfPhotoID,
            title = TypesOfPhotoIDConstants.titleId,
        )
    }
}
