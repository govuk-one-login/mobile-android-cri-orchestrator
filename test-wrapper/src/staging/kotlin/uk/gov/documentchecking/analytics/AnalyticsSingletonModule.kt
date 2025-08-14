package uk.gov.documentchecking.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.logging.MemorisedAnalyticsLogger
import uk.gov.logging.impl.analytics.FirebaseAnalyticsLogger
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AnalyticsSingletonModule {
    @Provides
    @Singleton
    fun providesAnalyticsLogger(analyticsLogger: FirebaseAnalyticsLogger): AnalyticsLogger =
        MemorisedAnalyticsLogger(analyticsLogger)
}

@InstallIn(SingletonComponent::class)
@Module
class FirebaseSingletonModule {
    @Provides
    @Singleton
    fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
}
