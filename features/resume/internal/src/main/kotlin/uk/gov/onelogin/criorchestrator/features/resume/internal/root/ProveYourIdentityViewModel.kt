package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.RefreshActiveSession

class ProveYourIdentityViewModel(
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
    val actions: Flow<ProveYourIdentityRootUiAction> = _actions.asSharedFlow()

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

                            // Trigger the modal to show automatically when the session becomes active.
                            // The user may have manually hidden the modal during an ongoing session.
                            // If they have manually hidden the modal, don't show it again.
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
