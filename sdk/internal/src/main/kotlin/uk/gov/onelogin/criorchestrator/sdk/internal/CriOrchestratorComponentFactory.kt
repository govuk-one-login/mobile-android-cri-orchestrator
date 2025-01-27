package uk.gov.onelogin.criorchestrator.sdk.internal

import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

/**
 * Creates instance of [BaseCriOrchestratorComponent].
 *
 * @param authenticatedHttpClient The HTTP client to make all network calls.
 * @return An instance of [CriOrchestratorComponent]
 */
fun createCriOrchestratorComponent(
    authenticatedHttpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
): CriOrchestratorComponent =
    DaggerMergedBaseCriOrchestratorComponent.factory().create(
        authenticatedHttpClient = authenticatedHttpClient,
        analyticsLogger = analyticsLogger
    )
