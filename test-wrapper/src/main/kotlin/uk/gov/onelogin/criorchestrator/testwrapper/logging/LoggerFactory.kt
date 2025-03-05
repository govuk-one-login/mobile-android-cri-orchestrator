package uk.gov.onelogin.criorchestrator.testwrapper.logging

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.jetbrains.annotations.VisibleForTesting
import uk.gov.logging.api.Logger
import uk.gov.logging.impl.AndroidLogger
import uk.gov.logging.impl.CrashlyticsLogger

object LoggerFactory {
    @VisibleForTesting
    internal var testLogger: Logger? = null

    fun createLogger(): Logger = testLogger ?: AndroidLogger(CrashlyticsLogger(FirebaseCrashlytics.getInstance()))
}
