package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@Composable
@Suppress("LongParameterList")
fun MainContent(
    criOrchestratorSdk: CriOrchestratorSdk,
    onSubUpdateRequest: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            criOrchestratorSdk = criOrchestratorSdk,
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
