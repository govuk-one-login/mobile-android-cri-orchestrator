package uk.gov.onelogin.criorchestrator.features.handback.internal.analytics

import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.onelogin.criorchestrator.libraries.analytics.Analytics
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.ResourceProvider
import javax.inject.Inject

class HandbackAnalytics
    @Inject
    constructor(
        resourceProvider: ResourceProvider,
        analyticsLogger: AnalyticsLogger,
    ) : Analytics(
            resourceProvider,
            analyticsLogger,
            requiredParameters = requiredParameters,
        ) {
        companion object {
            internal val requiredParameters =
                RequiredParameters(
                    taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                    taxonomyLevel3 = TaxonomyLevel3.DOCUMENT_SELECTION,
                )
        }
    }
