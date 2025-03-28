package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics

import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.Organisation
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel1
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.onelogin.criorchestrator.libraries.analytics.Analytics
import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.ResourceProvider
import javax.inject.Inject

class SelectBrpAnalytics
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
                    organisation = Organisation.OT1056,
                    taxonomyLevel1 = TaxonomyLevel1.ONE_LOGIN,
                    taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                    taxonomyLevel3 = TaxonomyLevel3.DOCUMENT_SELECTION,
                )
        }
    }

enum class SelectBrpScreenId(
    override val rawId: String,
) : ScreenId {
    SelectBrp(rawId = "8349d732-fb9e-49f0-9b7c-5ed84ffe4613"),
}
