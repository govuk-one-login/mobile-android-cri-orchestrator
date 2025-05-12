package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType
import kotlin.time.Duration.Companion.seconds

class ConfirmNoNonChippedIDViewModel(
    private val analytics: SelectDocAnalytics,
    private val sessionStore: SessionStore,
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
            val session =
                sessionStore
                    .read()
                    .filterNotNull()
                    .timeout(5.seconds)
                    .first()

            if (session.journeyType == JourneyType.DesktopAppDesktop) {
                _action.emit(ConfirmNoNonChippedIDAction.NavigateToConfirmAbortDesktop)
            } else {
                _action.emit(ConfirmNoNonChippedIDAction.NavigateToConfirmAbortMobile)
            }
        }
    }
}
