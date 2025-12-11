package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(FaceScanLimitReachedDesktopViewModel::class)
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
