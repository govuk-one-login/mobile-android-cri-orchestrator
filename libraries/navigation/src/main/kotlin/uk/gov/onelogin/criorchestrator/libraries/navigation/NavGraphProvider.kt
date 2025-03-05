package uk.gov.onelogin.criorchestrator.libraries.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * Provides all or part of a navigation graph.
 *
 * Use this together with [CompositeNavHost] to combine navigation graphs into a single graph.
 */
fun interface NavGraphProvider {
    /**
     * Calls functions on [NavGraphBuilder] to construct a navigation graph.
     */
    fun NavGraphBuilder.contributeToGraph(navController: NavController)
}
