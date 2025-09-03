package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.CriOrchestratorViewModelScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelKey

@ContributesIntoMap(CriOrchestratorViewModelScope::class, binding = binding<ViewModel>())
@ViewModelKey(ProveYourIdentityViewModel::class)
class ProveYourIdentityViewModel @Inject constructor(
    private val isSessionResumable: IsSessionResumable,
    private val refreshActiveSession: RefreshActiveSession,
    private val analytics: ResumeAnalytics,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(),
    LogTagProvider {
    companion object {
        const val SAVED_SHOW_CARD = "saved_is_session_resumable"
    }

    private var savedShowCard: Boolean?
        get() = savedStateHandle[SAVED_SHOW_CARD]
        set(value) {
            savedStateHandle[SAVED_SHOW_CARD] = value
        }

    private val _state =
        MutableStateFlow<ProveYourIdentityRootUiState>(
            ProveYourIdentityRootUiState(
                showCard = savedShowCard ?: false,
            ),
        )
    val state: StateFlow<ProveYourIdentityRootUiState> = _state

    private val _actions = MutableSharedFlow<ProveYourIdentityRootUiAction>()
    val actions: SharedFlow<ProveYourIdentityRootUiAction> = _actions.asSharedFlow()

    private var isResumableJob: Job? = null

    fun onScreenStart() {
        viewModelScope.launch {
            isResumableJob?.cancelAndJoin()
            isResumableJob =
                launch {
                    refreshActiveSession()
                    isSessionResumable()
                        .collect { isSessionResumable ->
                            val newShowCard = isSessionResumable
                            _state.value = _state.value.copy(showCard = newShowCard)

                            // Trigger the modal to show automatically only when the card transitions from being hidden
                            // to being shown (that is, when a session becomes 'active').
                            // After this, the user may hide the modal. After this, they must manually re-open it by
                            // clicking on the card.
                            if (savedShowCard != true && newShowCard == true) {
                                _actions.emit(ProveYourIdentityRootUiAction.AllowModalToShow)
                            }

                            savedShowCard = newShowCard
                        }
                }
        }
    }

    fun onStartClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.start_id_check_primary_button,
        )
    }

    fun onModalCancelClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.cancel_button_analytics_text,
        )
    }
}
