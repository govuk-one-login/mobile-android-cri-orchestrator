package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(ReturnToMobileWebViewModel::class)
class ReturnToMobileWebViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ReturnToMobileWebAction>()
    val actions: SharedFlow<ReturnToMobileWebAction> = _actions.asSharedFlow()

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ReturnToMobileWeb,
            title = ReturnToMobileWebConstants.titleId,
        )
    }

    fun onContinueToGovUk() {
        analytics.trackButtonEvent(ReturnToMobileWebConstants.buttonId)

        viewModelScope.launch {
            _actions.emit(ReturnToMobileWebAction.ContinueToGovUk)
        }
    }
}
