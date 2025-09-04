package uk.gov.onelogin.criorchestrator.sdk.internal

import android.app.Activity
import android.content.Context
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Includes
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import uk.gov.onelogin.criorchestrator.sdk.internal.di.ViewModelGraph

/**
 * The real dependency graph that other component interfaces and modules will be merged into.
 */
@DependencyGraph(
    CriOrchestratorScope::class,
)
interface BaseCriOrchestratorGraph : ViewModelGraph.Factory {
    @DependencyGraph.Factory
    interface Factory {
        @Suppress("LongParameterList")
        fun create(
            @Includes baseAppGraph: BaseCriOrchestratorAppGraph,
            @Provides context: Context,
            @Provides activity: Activity,
        ): BaseCriOrchestratorGraph
    }
}
