package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
) {
    val criOrchestratorComponent =
        rememberCriOrchestrator(
            criOrchestratorSdk = criOrchestratorSdk,
        )

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    /**
     * TODO: Can we reproduce this bug in a less intrusive way?
     *   It won't be fun for the test wrapper to behave like this during manual testing.
     *   Perhaps we can provide another mechanism to navigate forward to another screen that tests can use.
     */
    LifecycleEventEffect(
        event = Lifecycle.Event.ON_START,
    ) {
        navController.navigate(NavDestination.Locked)
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
        composable<NavDestination.Locked> {
            LockScreen(
                onUnlock = {
                    navController.popBackStack()
                },
            )
        }
    }
}

private sealed class NavDestination {
    @Serializable
    object Setup

    @Serializable
    object Home

    @Serializable
    object Locked
}
