package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.session.publicapi.refreshActiveSession
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@OptIn(UnstableDesignSystemAPI::class)
@Composable
@Suppress("LongParameterList")
fun MainContent(
    criOrchestratorSdk: CriOrchestratorSdk,
    onSubUpdateRequest: (String?) -> Unit,
    modifier: Modifier = Modifier,
    testActions: Flow<MainContentTestAction> = flowOf(),
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            criOrchestratorSdk = criOrchestratorSdk,
        )

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        testActions.collect {
            when (it) {
                MainContentTestAction.NavigateToAnotherScreen -> navController.navigate(NavDestination.Another)
            }
        }
    }

    NavHost(
        modifier = modifier,
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
                criOrchestratorComponent = criOrchestratorComponent,
                onRefreshActiveSessionClick = {
                    coroutineScope.launch {
                        criOrchestratorSdk.refreshActiveSession()
                    }
                },
            )
        }
        composable<NavDestination.Another> {
            AnotherScreen()
        }
    }
}

private sealed class NavDestination {
    @Serializable
    object Setup

    @Serializable
    object Home

    @Serializable
    object Another
}

enum class MainContentTestAction {
    NavigateToAnotherScreen,
}
