package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktop

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class ConfirmAbortReturnToDesktopViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortReturnToDesktop,
            title = ConfirmAbortReturnToDesktopConstants.titleId,
        )
    }
}
