package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.CriOrchestratorViewModelScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelKey

@ContributesIntoMap(CriOrchestratorViewModelScope::class, binding = binding<ViewModel>())
@ViewModelKey(FaceScanLimitReachedMobileViewModel::class)
@Inject
class FaceScanLimitReachedMobileViewModel(
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<FaceScanLimitReachedMobileAction>()
    val actions: SharedFlow<FaceScanLimitReachedMobileAction> = _actions.asSharedFlow()

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.FaceScanLimitReachedMobile,
            title = FaceScanLimitReachedMobileConstants.titleId,
        )
    }

    fun onButtonClick() {
        analytics.trackButtonEvent(FaceScanLimitReachedMobileConstants.buttonId)

        viewModelScope.launch {
            _actions.emit(FaceScanLimitReachedMobileAction.ContinueToGovUk)
        }
    }
}
