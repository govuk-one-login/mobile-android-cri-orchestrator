package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.testwrapper.logging.AnalyticsLoggerFactory.createAnalyticsLogger
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient

@Composable
fun MainContent(
    httpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
        )
    ProveYourIdentityCard(
        component = criOrchestratorComponent,
        modifier = Modifier,
    )
}

@Composable
@PreviewLightDark
fun MainContentPreview() =
    GdsTheme {
        MainContent(
            httpClient = createHttpClient(),
            analyticsLogger = createAnalyticsLogger(LocalContext.current),
        )
    }
