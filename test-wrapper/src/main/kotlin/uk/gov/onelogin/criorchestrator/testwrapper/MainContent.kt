package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator

@Composable
fun MainContent(
    httpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
    logger: Logger,
    modifier: Modifier = Modifier,
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
            logger = logger,
        )
    ProveYourIdentityCard(
        component = criOrchestratorComponent,
        modifier = modifier,
    )
}
