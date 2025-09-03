package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.CriOrchestratorViewModelScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelKey

@ContributesIntoMap(CriOrchestratorViewModelScope::class, binding = binding<ViewModel>())
@ViewModelKey(ConfirmNoNonChippedIDViewModel::class)
class ConfirmNoNonChippedIDViewModel @Inject constructor(
    private val analytics: SelectDocAnalytics,
    private val getJourneyType: GetJourneyType,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmNoNonChippedIDAction>()
    val action: Flow<ConfirmNoNonChippedIDAction> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.ConfirmNoNonChippedID,
            ConfirmNoNonChippedIDConstants.titleId,
        )
    }

    @OptIn(FlowPreview::class)
    fun onConfirmClick() {
        analytics.trackButtonEvent(ConfirmNoNonChippedIDConstants.confirmButtonTextId)

        viewModelScope.launch {
            when (getJourneyType()) {
                is JourneyType.MobileAppMobile ->
                    _action.emit(ConfirmNoNonChippedIDAction.NavigateToConfirmAbortMobile)

                JourneyType.DesktopAppDesktop ->
                    _action.emit(ConfirmNoNonChippedIDAction.NavigateToConfirmAbortDesktop)
            }
        }
    }
}
