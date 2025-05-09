package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortmobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
class ConfirmAbortMobileViewModel(
    private val sessionStore: SessionStore,
    private val analytics: HandbackAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<ConfirmAbortMobileAction>()
    val actions: SharedFlow<ConfirmAbortMobileAction> = _actions.asSharedFlow()

    fun onScreenStart() {
        analytics.trackScreen(
            id = HandbackScreenId.ConfirmAbortToMobile,
            title = ConfirmAbortMobileConstants.titleId,
        )
    }

    fun onContinueToGovUk() {
        analytics.trackButtonEvent(ConfirmAbortMobileConstants.buttonId)

        viewModelScope.launch {
            val redirectUri =
                sessionStore
                    .read()
                    .filterNotNull()
                    .first()
                    .redirectUri

            redirectUri?.let {
                _actions.emit(ConfirmAbortMobileAction.ContinueGovUk(it))
            }
        }
    }
}
