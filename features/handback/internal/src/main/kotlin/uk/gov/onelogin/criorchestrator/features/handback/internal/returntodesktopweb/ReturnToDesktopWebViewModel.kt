package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.RequestAppReview
import kotlin.time.Duration.Companion.seconds

class ReturnToDesktopWebViewModel(
    private val analytics: HandbackAnalytics,
    private val requestAppReview: RequestAppReview,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ReturnToDesktopWeb,
            title = ReturnToDesktopWebConstants.titleId,
        )

        viewModelScope.launch {
            delay(2.seconds)
            requestAppReview()
        }
    }
}
