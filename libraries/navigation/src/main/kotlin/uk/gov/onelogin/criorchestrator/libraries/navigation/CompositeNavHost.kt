package uk.gov.onelogin.criorchestrator.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.ImmutableSet

/**
 * [NavHost] that builds a graph from multiple [NavGraphProvider]s.
 *
 * @param[navGraphProviders] Set of navigation graph providers that contribute to the final graph
 * @param[startDestination] The route for the start destination
 * @param[modifier] A [Modifier] to apply to the layout
 */
@Composable
fun CompositeNavHost(
    navGraphProviders: ImmutableSet<NavGraphProvider>,
    startDestination: NavigationDestination,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        for (navGraphProvider in navGraphProviders) {
            with(navGraphProvider) {
                contributeToGraph(navController)
            }
        }
    }
}
