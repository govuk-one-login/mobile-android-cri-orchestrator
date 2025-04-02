package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator

@Composable
@Suppress("LongParameterList")
fun MainContent(
    analyticsLogger: AnalyticsLogger,
    config: Config,
    logger: Logger,
    httpClient: GenericHttpClient,
    onSubUpdateRequest: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = config,
            logger = logger,
        )

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.Setup,
    ) {
        composable<NavDestination.Setup> {
            SetupScreen(
                onSubUpdateRequest = onSubUpdateRequest,
                onStartClick = { navController.navigate(NavDestination.Home) },
                criOrchestratorComponent = criOrchestratorComponent,
            )
        }
        composable<NavDestination.Home> {
            HomeScreen(
                modifier = modifier,
                criOrchestratorComponent = criOrchestratorComponent,
            )
        }
    }
}

private sealed class NavDestination {
    @Serializable
    object Setup

    @Serializable
    object Home
}
