package uk.gov.onelogin.criorchestrator.sdk.publicapi

import android.content.Context
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.internal.CriOrchestratorSingletonImpl
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

object CriOrchestratorSdkExt {
    /**
     * Creates a [CriOrchestratorSdk] instance.
     *
     * Singleton behaviour is not enforced by this function so that consuming apps can manage the
     * object however they choose.
     *
     * Take care to ensure that only one instance of this object is created.
     */
    fun CriOrchestratorSdk.Companion.create(
        authenticatedHttpClient: GenericHttpClient,
        analyticsLogger: AnalyticsLogger,
        initialConfig: Config,
        logger: Logger,
        applicationContext: Context,
    ): CriOrchestratorSdk =
        CriOrchestratorSingletonImpl(
            authenticatedHttpClient = authenticatedHttpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = initialConfig,
            logger = logger,
            applicationContext = applicationContext,
        )
}
