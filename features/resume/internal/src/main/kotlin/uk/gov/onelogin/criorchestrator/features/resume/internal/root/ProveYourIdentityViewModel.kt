package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.ResourceProvider

internal class ProveYourIdentityViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val sessionReader: SessionReader,
    private val analyticsLogger: AnalyticsLogger,
    private val resourceProvider: ResourceProvider,
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
        val proveIdentityEvent =
            TrackEvent.Button(
                text = resourceProvider.getEnglishString(R.string.start_id_check_primary_button),
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            )
        analyticsLogger.logEventV3Dot1(proveIdentityEvent)
    }

    private fun checkActiveSession() {
        viewModelScope.launch(dispatcher) {
            val hasActiveSession = sessionReader.isActiveSession()
            logger.debug(tag, "Has active session: $hasActiveSession")
            _state.value = _state.value.copy(shouldDisplay = hasActiveSession)
        }
    }
}
