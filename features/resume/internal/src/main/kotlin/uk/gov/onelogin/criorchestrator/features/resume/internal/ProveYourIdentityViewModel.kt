package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModel
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent

internal class ProveYourIdentityViewModel(
    private val analyticsLogger: AnalyticsLogger,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {
    fun start() {
        val proveIdentityEvent =
            TrackEvent.Button(
                text = resourceProvider.getEnglishString(R.string.start_id_check_primary_button),
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.PASSPORT_CRI,
                    ),
            )
        analyticsLogger.logEventV3Dot1(proveIdentityEvent)
    }
}
