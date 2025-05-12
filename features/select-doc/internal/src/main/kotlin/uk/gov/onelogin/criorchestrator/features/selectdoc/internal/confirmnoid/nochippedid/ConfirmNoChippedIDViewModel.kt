package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType

class ConfirmNoChippedIDViewModel(
    private val analytics: SelectDocAnalytics,
    private val getJourneyType: GetJourneyType,
) : ViewModel() {
    private val _action = MutableSharedFlow<ConfirmNoChippedIDAction>()
    val action: Flow<ConfirmNoChippedIDAction> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            SelectDocScreenId.ConfirmNoChippedID,
            ConfirmNoChippedIDConstants.titleId,
        )
    }

    @OptIn(FlowPreview::class)
    fun onConfirmClick() {
        analytics.trackButtonEvent(ConfirmNoChippedIDConstants.confirmButtonTextId)

        viewModelScope.launch {
            when (getJourneyType()) {
                JourneyType.MobileAppMobile ->
                    _action.emit(ConfirmNoChippedIDAction.NavigateToConfirmAbortMobile)

                JourneyType.DesktopAppDesktop ->
                    _action.emit(ConfirmNoChippedIDAction.NavigateToConfirmAbortDesktop)
            }
        }
    }
}
