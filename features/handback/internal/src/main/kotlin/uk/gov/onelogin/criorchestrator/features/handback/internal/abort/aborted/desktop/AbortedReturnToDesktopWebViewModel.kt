package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(AbortedReturnToDesktopWebViewModel::class)
class AbortedReturnToDesktopWebViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.AbortedReturnToDesktopWeb,
            title = AbortedReturnToDesktopWebConstants.titleId,
        )
    }
}
