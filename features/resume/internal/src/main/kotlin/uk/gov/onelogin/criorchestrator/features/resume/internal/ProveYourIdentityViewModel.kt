package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.session.internalapi.GetActiveSessionUseCase
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.ResourceProvider
import javax.inject.Inject

internal class ProveYourIdentityViewModel(
    private val getActiveSessionUseCase: GetActiveSessionUseCase,
    private val analyticsLogger: AnalyticsLogger,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {
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
        CoroutineScope(Dispatchers.Default).launch {
            var hasActiveSession = getActiveSessionUseCase.execute()
            // TODO: remove print statement
            println("hasActiveSession: $hasActiveSession")
            _state.value = _state.value.copy(shouldDisplay = hasActiveSession)
        }
    }
}
