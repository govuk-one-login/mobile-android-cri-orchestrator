package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortreturntodesktopweb

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class ConfirmAbortReturnToDesktopWebViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortReturnToDesktopWeb,
            title = ConfirmAbortReturnToDesktopWebConstants.titleId,
        )
    }
}
