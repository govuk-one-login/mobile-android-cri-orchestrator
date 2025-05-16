package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType

internal class UnrecoverableErrorViewModel(
    private val analytics: HandbackAnalytics,
    private val getJourneyType: GetJourneyType,
) : ViewModel() {
    private val _actions = MutableSharedFlow<UnrecoverableErrorAction>()
    val actions: Flow<UnrecoverableErrorAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.UnrecoverableError,
            title = UnrecoverableErrorConstants.titleId,
        )
    }

    fun onButtonClick() {
        analytics.trackButtonEvent(
            buttonText = UnrecoverableErrorConstants.buttonTextId,
        )

        viewModelScope.launch {
            val action =
                when (getJourneyType()) {
                    JourneyType.DesktopAppDesktop ->
                        UnrecoverableErrorAction.NavigateToConfirmAbortDesktop
                    is JourneyType.MobileAppMobile ->
                        UnrecoverableErrorAction.NavigateToConfirmAbortMobile
                }

            _actions.emit(action)
        }
    }
}
