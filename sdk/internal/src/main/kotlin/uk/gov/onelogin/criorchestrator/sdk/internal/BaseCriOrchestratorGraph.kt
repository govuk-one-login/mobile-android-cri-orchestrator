package uk.gov.onelogin.criorchestrator.sdk.internal

import android.app.Activity
import android.content.Context
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Includes
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelFactoryBindings

/**
 * The real dependency graph that other component interfaces and modules will be merged into.
 */
@DependencyGraph(
    CriOrchestratorScope::class,
    bindingContainers = [ViewModelFactoryBindings::class],
)
interface BaseCriOrchestratorGraph : ViewModelGraph {
    @DependencyGraph.Factory
    fun interface Factory {
        @Suppress("LongParameterList")
        fun create(
            @Includes baseAppGraph: BaseCriOrchestratorAppGraph,
            @Provides context: Context,
            @Provides activity: Activity,
        ): BaseCriOrchestratorGraph
    }
}
