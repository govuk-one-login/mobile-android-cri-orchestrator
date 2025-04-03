package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSingletonComponent

/**
 * @param authenticatedHttpClient The HTTP client to make all network calls.
 * @param analyticsLogger The analytics logger that will receive events from the SDK.
 * @param initialConfig The configuration to initialise the SDK.
 *   Configuration may be updated through the developer menu.
 * @param logger The logger that logs events to the system and to Crashlytics.
 * @param applicationContext The host application
 */
class CriOrchestratorSingletonImpl(
    authenticatedHttpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
    initialConfig: Config,
    logger: Logger,
    applicationContext: Context,
) : CriOrchestratorSdk {
    override val component: CriOrchestratorSingletonComponent =
        DaggerMergedBaseCriOrchestratorSingletonComponent.factory().create(
            authenticatedHttpClient = authenticatedHttpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = initialConfig,
            logger = logger,
            applicationContext = applicationContext,
        )
}
