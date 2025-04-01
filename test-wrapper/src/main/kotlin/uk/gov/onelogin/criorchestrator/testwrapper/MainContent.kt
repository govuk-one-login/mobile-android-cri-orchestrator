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
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.testwrapper.devmenu.DevMenuDialog
import uk.gov.onelogin.criorchestrator.testwrapper.devmenu.DevMenuFloatingActionButton

@Composable
fun MainContent(
    httpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
    config: Config,
    logger: Logger,
    didUpdateSub: (String) -> Unit,
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

        EnterTextDialog { sub ->
            didUpdateSub(sub)
        }

        if (showDevMenu) {
            DevMenuDialog(
                criOrchestratorComponent = criOrchestratorComponent,
                onDismissRequest = { showDevMenu = false },
            )
        }
    }
}