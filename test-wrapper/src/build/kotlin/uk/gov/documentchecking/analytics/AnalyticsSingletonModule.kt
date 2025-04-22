package uk.gov.documentchecking.analytics

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.logging.MemorisedAnalyticsLogger
import uk.gov.logging.testdouble.analytics.ToastingAnalyticsLogger
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AnalyticsSingletonModule {
    @Provides
    @Singleton
    fun providesAnalyticsLogger(
        analyticsLogger: ToastingAnalyticsLogger
    ): AnalyticsLogger = MemorisedAnalyticsLogger(analyticsLogger)
}
