package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.testwrapper.devmenu.DevMenuDialog
import uk.gov.onelogin.criorchestrator.testwrapper.devmenu.DevMenuFloatingActionButton
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient

@Composable
fun MainContent(
    httpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
    config: Config,
    logger: Logger,
    modifier: Modifier = Modifier,
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = config,
            logger = logger,
        )
    var showDevMenu by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            DevMenuFloatingActionButton(
                onClick = { showDevMenu = true },
            )
        },
    ) { innerPadding ->
        ProveYourIdentityCard(
            component = criOrchestratorComponent,
            modifier = modifier.padding(innerPadding),
        )

        if (showDevMenu) {
            DevMenuDialog(
                criOrchestratorComponent = criOrchestratorComponent,
                onDismissRequest = { showDevMenu = false },
            )
        }
    }
}

@Composable
@PreviewLightDark
internal fun MainContentPreview() {
    val config = TestWrapperConfig.provideConfig(LocalContext.current.resources)
    GdsTheme {
        MainContent(
            httpClient = createHttpClient(),
            analyticsLogger = FakeAnalyticsLogger(),
            config = config,
            logger = SystemLogger(),
            modifier = Modifier,
        )
    }
}
