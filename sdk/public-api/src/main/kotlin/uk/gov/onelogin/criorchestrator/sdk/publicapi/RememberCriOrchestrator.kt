package uk.gov.onelogin.criorchestrator.sdk.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.internal.createCriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

/**
 * Creates and remembers the shared state for the Credential Issuer (CRI) Orchestrator SDK.
 *
 * @param authenticatedHttpClient The HTTP client to make all network calls.
 * @param analyticsLogger The analytics logger that will receive events from the SDK.
 * @param logger The logger that logs events to the system and to Crashlytics.
 * @return An instance of [CriOrchestratorComponent]
 */
@Composable
fun rememberCriOrchestrator(
    authenticatedHttpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
    initialConfig: Config,
    logger: Logger,
): CriOrchestratorComponent {
    val context = LocalContext.current
    return remember {
        createCriOrchestratorComponent(
            authenticatedHttpClient = authenticatedHttpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = initialConfig,
            logger = logger,
            context = context,
        )
    }
}
