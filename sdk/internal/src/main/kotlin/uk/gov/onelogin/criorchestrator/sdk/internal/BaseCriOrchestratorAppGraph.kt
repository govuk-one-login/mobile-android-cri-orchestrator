package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.libraries.architecture.CriOrchestratorService
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers
import uk.gov.onelogin.criorchestrator.sdk.internal.di.CoroutineDispatchersBindings.Companion.TEST_DISPATCHER_NAME

/**
 * The real dependency graph that other graph interfaces and binding containers will be merged into.
 */
@DependencyGraph(CriOrchestratorAppScope::class)
interface BaseCriOrchestratorAppGraph {
    @DependencyGraph.Factory
    interface Factory {
        @Suppress("LongParameterList")
        fun create(
            @Provides authenticatedHttpClient: GenericHttpClient,
            @Provides analyticsLogger: AnalyticsLogger,
            @Provides initialConfig: Config,
            @Provides logger: Logger,
            @Provides applicationContext: Context,
            @Provides @Named(TEST_DISPATCHER_NAME) testDispatcher: CoroutineDispatcher?,
        ): BaseCriOrchestratorAppGraph
    }

    fun coroutineScope(): CoroutineScope

    fun coroutineDispatchers(): CoroutineDispatchers

    fun services(): Set<CriOrchestratorService>

    fun logger(): Logger

    fun analyticsLogger(): AnalyticsLogger

    fun authenticatedHttpClient(): GenericHttpClient
}
