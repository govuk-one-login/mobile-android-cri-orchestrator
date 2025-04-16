package uk.gov.documentchecking.analytics

import android.content.Context
import android.nfc.NfcManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.gov.logging.api.Logger
import javax.inject.Singleton
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.logging.MemorisedAnalyticsLogger
import uk.gov.logging.impl.analytics.FirebaseAnalyticsLogger
import uk.gov.logging.testdouble.SystemLogger

@InstallIn(SingletonComponent::class)
@Module
object AnalyticsSingletonModule {
    @Provides
    @Singleton
    fun providesAnalyticsLogger(
        analyticsLogger: FirebaseAnalyticsLogger
    ): AnalyticsLogger = MemorisedAnalyticsLogger(analyticsLogger)
}

@InstallIn(SingletonComponent::class)
@Module
class FirebaseSingletonModule {

    @Provides
    @Singleton
    fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
}

@InstallIn(SingletonComponent::class)
@Module
class LoggerModule {
    @Provides
    @Singleton
    fun providesLogger(): Logger = SystemLogger()
}
