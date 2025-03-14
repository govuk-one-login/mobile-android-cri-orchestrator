package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader

internal class ProveYourIdentityViewModel(
    private val sessionReader: SessionReader,
    private val analytics: ResumeAnalytics,
    private val logger: Logger,
) : ViewModel(),
    LogTagProvider {
    private val _state =
        MutableStateFlow<ProveYourIdentityRootUiState>(
            ProveYourIdentityRootUiState(
                shouldDisplay = false,
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

    private fun checkActiveSession() {
        viewModelScope.launch {
            sessionReader.isActiveSession().collect { isActiveSession ->
                logger.debug(
                    tag,
                    "Collected isActiveSession $isActiveSession",
                )
                _state.value = _state.value.copy(shouldDisplay = isActiveSession)
            }
        }
    }
}
