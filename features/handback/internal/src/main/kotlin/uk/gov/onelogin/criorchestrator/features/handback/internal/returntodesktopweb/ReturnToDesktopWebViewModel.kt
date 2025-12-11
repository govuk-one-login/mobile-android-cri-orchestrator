package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.RequestAppReview
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import kotlin.time.Duration.Companion.seconds

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(ReturnToDesktopWebViewModel::class)
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
