package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.TestWrapperConfig
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
        resources: Resources,
    ): CriOrchestratorSdk =
        CriOrchestratorSdk.create(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = TestWrapperConfig.provideConfig(resources),
            logger = logger,
            applicationContext = application,
        )
}
