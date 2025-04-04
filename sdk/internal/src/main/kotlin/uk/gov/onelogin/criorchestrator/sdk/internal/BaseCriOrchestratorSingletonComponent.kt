package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Singleton

/**
 * The real Dagger component that other component interfaces and modules will be merged into.
 */
@Singleton
@MergeComponent(CriOrchestratorSingletonScope::class)
interface BaseCriOrchestratorSingletonComponent {
    @MergeComponent.Factory
    interface Factory {
        fun create(
            @BindsInstance authenticatedHttpClient: GenericHttpClient,
            @BindsInstance analyticsLogger: AnalyticsLogger,
            @BindsInstance initialConfig: Config,
            @BindsInstance logger: Logger,
            @BindsInstance applicationContext: Context,
        ): BaseCriOrchestratorSingletonComponent
    }

    fun logger(): Logger

    fun analyticsLogger(): AnalyticsLogger

    fun authenticatedHttpClient(): GenericHttpClient
}
