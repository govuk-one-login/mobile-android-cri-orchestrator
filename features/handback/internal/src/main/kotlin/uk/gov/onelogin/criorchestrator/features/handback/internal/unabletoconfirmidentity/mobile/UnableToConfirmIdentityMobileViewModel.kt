package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
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
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
class UnableToConfirmIdentityMobileViewModel(
    private val sessionStore: SessionStore,
    private val analytics: HandbackAnalytics,
    private val abortSession: AbortSession,
    private val logger: Logger,
) : ViewModel(),
    LogTagProvider {
    private val _state = MutableStateFlow<ConfirmAbortState>(ConfirmAbortState.Display)
    val state = _state.asStateFlow()
    private val _actions = MutableSharedFlow<UnableToConfirmIdentityMobileAction>()
    val actions: SharedFlow<UnableToConfirmIdentityMobileAction> = _actions.asSharedFlow()

    fun onScreenStart() {
        _state.value = ConfirmAbortState.Display
        analytics.trackScreen(
            // TODO: update id
            id = HandbackScreenId.ConfirmAbortMobile,
            title = UnableToConfirmIdentityMobileConstants.titleId,
        )
    }

    fun onContinueToGovUk() {
        _state.value = ConfirmAbortState.Loading
        analytics.trackButtonEvent(UnableToConfirmIdentityMobileConstants.buttonId)

        viewModelScope.launch {
            val redirectUri =
                sessionStore
                    .read()
                    .first()
                    ?.redirectUri

            when (abortSession()) {
                AbortSession.Result.Error.Offline ->
                    _actions.emit(UnableToConfirmIdentityMobileAction.NavigateToOfflineError)
                is AbortSession.Result.Error.Unrecoverable ->
                    _actions.emit(UnableToConfirmIdentityMobileAction.NavigateToUnrecoverableError)
                AbortSession.Result.Success -> {
                    if (redirectUri == null) {
                        logger.error(tag, "Can't continue to GOV.UK - no redirect URI")
                        _actions.emit(UnableToConfirmIdentityMobileAction.NavigateToUnrecoverableError)
                    } else {
                        _actions.emit(UnableToConfirmIdentityMobileAction.ContinueGovUk(redirectUri))
                    }
                }
            }
        }
    }
}
