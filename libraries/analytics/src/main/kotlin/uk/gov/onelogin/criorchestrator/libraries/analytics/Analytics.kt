package uk.gov.onelogin.criorchestrator.libraries.analytics

import androidx.annotation.StringRes
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.ResourceProvider

interface ScreenId {
    val rawId: String
}

abstract class Analytics(
    private val resourceProvider: ResourceProvider,
    private val analyticsLogger: AnalyticsLogger,
    private val requiredParameters: RequiredParameters,
) {
    fun trackButtonEvent(
        @StringRes buttonText: Int,
    ) = analyticsLogger.logEventV3Dot1(
        TrackEvent.Button(
            text = resourceProvider.getEnglishString(buttonText),
            params = requiredParameters,
        ),
    )

    fun trackFormSubmission(
        @StringRes buttonText: Int,
        @StringRes response: Int,
    ) = analyticsLogger.logEventV3Dot1(
        TrackEvent.Form(
            text = resourceProvider.getEnglishString(buttonText),
            response = resourceProvider.getEnglishString(response),
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
