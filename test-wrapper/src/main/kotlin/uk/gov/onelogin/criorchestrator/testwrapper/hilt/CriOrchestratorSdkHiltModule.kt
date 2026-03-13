package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CriOrchestratorSdkHiltModule {
    @Provides
    @Singleton
    fun providesCriOrchestratorSdk(
        analyticsLogger: AnalyticsLogger,
        application: Application,
        logger: Logger,
        httpClient: GenericHttpClient,
        initialConfig: Config,
    ): CriOrchestratorSdk =
        CriOrchestratorSdk.create(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = initialConfig,
            logger = logger,
            applicationContext = application,
        )
}
