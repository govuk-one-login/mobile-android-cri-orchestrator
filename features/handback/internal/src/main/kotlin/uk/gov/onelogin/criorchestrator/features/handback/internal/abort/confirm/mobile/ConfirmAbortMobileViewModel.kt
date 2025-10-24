package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.CriOrchestratorViewModelScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelKey

@ContributesIntoMap(CriOrchestratorViewModelScope::class, binding = binding<ViewModel>())
@ViewModelKey(ConfirmAbortMobileViewModel::class)
class ConfirmAbortMobileViewModel(
    private val sessionStore: SessionStore,
    private val analytics: HandbackAnalytics,
    private val abortSession: AbortSession,
    private val logger: Logger,
) : ViewModel(),
    LogTagProvider {
    private val _state = MutableStateFlow<ConfirmAbortState>(ConfirmAbortState.Display)
    val state = _state.asStateFlow()
    private val _actions = MutableSharedFlow<ConfirmAbortMobileAction>()
    val actions: SharedFlow<ConfirmAbortMobileAction> = _actions.asSharedFlow()

    fun onScreenStart() {
        _state.value = ConfirmAbortState.Display
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortMobile,
            title = ConfirmAbortMobileConstants.titleId,
        )
    }

    fun onContinueToGovUk() {
        _state.value = ConfirmAbortState.Loading
        analytics.trackButtonEvent(ConfirmAbortMobileConstants.buttonId)

        viewModelScope.launch {
            val redirectUri =
                sessionStore
                    .read()
                    .first()
                    ?.redirectUri

            when (abortSession()) {
                AbortSession.Result.Error.Offline ->
                    _actions.emit(ConfirmAbortMobileAction.NavigateToOfflineError)
                is AbortSession.Result.Error.Unrecoverable ->
                    _actions.emit(ConfirmAbortMobileAction.NavigateToUnrecoverableError)
                AbortSession.Result.Success -> {
                    if (redirectUri == null) {
                        logger.error(tag, "Can't continue to GOV.UK - no redirect URI")
                        _actions.emit(ConfirmAbortMobileAction.NavigateToUnrecoverableError)
                    } else {
                        _actions.emit(ConfirmAbortMobileAction.ContinueGovUk(redirectUri))
                    }
                }
            }
        }
    }
}
