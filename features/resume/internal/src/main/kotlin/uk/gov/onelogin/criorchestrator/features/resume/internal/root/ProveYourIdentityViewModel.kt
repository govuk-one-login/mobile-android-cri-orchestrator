package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader

class ProveYourIdentityViewModel(
    private val sessionReader: SessionReader,
    private val analytics: ResumeAnalytics,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(),
    LogTagProvider {
    companion object {
        const val SHOULD_DISPLAY_KEY = "shouldDisplay"
    }

    private var savedShouldDisplay: Boolean?
        get() = savedStateHandle[SHOULD_DISPLAY_KEY]
        set(value) {
            savedStateHandle[SHOULD_DISPLAY_KEY] = value
        }

    private val _state =
        MutableStateFlow<ProveYourIdentityRootUiState>(
            ProveYourIdentityRootUiState(
                shouldDisplay = savedShouldDisplay ?: false,
            ),
        )
    val state: StateFlow<ProveYourIdentityRootUiState> = _state

    init {
        checkActiveSession()
    }

    fun start() {
        analytics.trackButtonEvent(
            buttonText = R.string.start_id_check_primary_button,
        )
    }

    fun onModalCancelClick() {
        analytics.trackButtonEvent(
            buttonText = R.string.cancel_button_analytics_text,
        )
    }

    private fun checkActiveSession() {
        if (savedShouldDisplay != null) {
            return
        }

        viewModelScope.launch {
            val isActiveSession = sessionReader.isActiveSession()
            _state.value = _state.value.copy(shouldDisplay = isActiveSession)
            savedShouldDisplay = isActiveSession
        }
    }
}
