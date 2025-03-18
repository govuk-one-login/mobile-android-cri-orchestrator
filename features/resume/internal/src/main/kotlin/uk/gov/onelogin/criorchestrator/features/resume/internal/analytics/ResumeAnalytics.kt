package uk.gov.onelogin.criorchestrator.features.resume.internal.analytics

import androidx.annotation.StringRes
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.ResourceProvider
import javax.inject.Inject

class ResumeAnalytics
    @Inject
    constructor(
        private val resourceProvider: ResourceProvider,
        private val analyticsLogger: AnalyticsLogger,
    ) {
        private val requiredParameters =
            RequiredParameters(
                taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                taxonomyLevel3 = TaxonomyLevel3.RESUME,
            )

        fun trackButtonEvent(
            @StringRes buttonText: Int,
        ) = analyticsLogger.logEventV3Dot1(
            TrackEvent.Button(
                text = resourceProvider.getEnglishString(buttonText),
                params = requiredParameters,
            ),
        )

        fun trackScreen(
            id: ScreenId,
            @StringRes title: Int,
        ) = analyticsLogger.logEventV3Dot1(
            ViewEvent.Screen(
                id = id.rawId,
                name = resourceProvider.getEnglishString(title),
                params = requiredParameters,
            ),
        )
    }
