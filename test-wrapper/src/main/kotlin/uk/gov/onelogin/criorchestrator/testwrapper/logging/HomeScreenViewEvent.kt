package uk.gov.onelogin.criorchestrator.testwrapper.logging

import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.parameters.ScreenViewParameters

internal fun homeScreenViewEvent(obj: Any) = ScreenViewParameters(
    clazz = obj::class.java.simpleName,
    name = "TestWrapper",
    title = "Test Wrapper app",
).let {
    AnalyticsEvent.screenView(it)
}
