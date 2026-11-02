package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDState
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(ConfirmNoNonChippedIDViewModel::class)
class ConfirmNoNonChippedIDViewModel(
    private val analytics: SelectDocAnalytics,
    private val getJourneyType: GetJourneyType,
    private val sessionStore: SessionStore,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmNoNonChippedIDAction>()
    val action: Flow<ConfirmNoNonChippedIDAction> = _action

    private val _state = MutableStateFlow(
        ConfirmNoNonChippedIDState(disableBrpJourney = extractDisableBrpJourney()),
    )
    val state: StateFlow<ConfirmNoNonChippedIDState> = _state

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

    private fun extractDisableBrpJourney(): Boolean = sessionStore.read().value?.disableBrpJourney ?: false
}
