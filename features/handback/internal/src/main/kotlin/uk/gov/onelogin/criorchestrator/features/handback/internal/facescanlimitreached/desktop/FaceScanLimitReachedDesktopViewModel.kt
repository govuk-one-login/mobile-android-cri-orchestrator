package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class FaceScanLimitReachedDesktopViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.FaceScanLimitReachedDesktop,
            title = FaceScanLimitReachedDesktopConstants.titleId,
        )
    }
}
