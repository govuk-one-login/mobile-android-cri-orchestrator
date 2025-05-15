package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

import androidx.lifecycle.ViewModel
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class AbortedReturnToDesktopViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortReturnToDesktop,
            title = AbortedReturnToDesktopConstants.titleId,
        )
    }
}
