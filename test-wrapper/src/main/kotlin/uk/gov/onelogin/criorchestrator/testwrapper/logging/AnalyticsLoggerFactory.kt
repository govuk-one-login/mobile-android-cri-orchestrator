package uk.gov.onelogin.criorchestrator.testwrapper.logging

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import org.jetbrains.annotations.VisibleForTesting
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.impl.analytics.FirebaseAnalyticsLogger

object AnalyticsLoggerFactory {
    @VisibleForTesting
    internal var testAnalyticsLogger: AnalyticsLogger? = null

    fun createAnalyticsLogger(
        context: Context,
        logger: Logger,
    ): AnalyticsLogger =
        testAnalyticsLogger ?: FirebaseAnalyticsLogger(
            analytics = FirebaseAnalytics.getInstance(context),
            logger = logger,
        )
}
