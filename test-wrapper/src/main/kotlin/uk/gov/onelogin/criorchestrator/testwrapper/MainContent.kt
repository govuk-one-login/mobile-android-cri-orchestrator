package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient

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

@Composable
@PreviewLightDark
internal fun MainContentPreview() =
    GdsTheme {
        MainContent(
            httpClient = createHttpClient(),
            analyticsLogger = FakeAnalyticsLogger(),
            logger = SystemLogger(),
            modifier = Modifier,
        )
    }
