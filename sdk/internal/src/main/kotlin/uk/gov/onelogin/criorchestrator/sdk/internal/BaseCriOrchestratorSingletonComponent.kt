package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate.IsIdCheckSdkActive
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.architecture.CriOrchestratorService
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers
import uk.gov.onelogin.criorchestrator.sdk.internal.di.CoroutineDispatchersModule.Companion.TEST_DISPATCHER_NAME
import javax.inject.Named
import javax.inject.Singleton

/**
 * The real Dagger component that other component interfaces and modules will be merged into.
 */
@Singleton
@MergeComponent(CriOrchestratorSingletonScope::class)
interface BaseCriOrchestratorSingletonComponent {
    @MergeComponent.Factory
    interface Factory {
        @Suppress("LongParameterList")
        fun create(
            @BindsInstance authenticatedHttpClient: GenericHttpClient,
            @BindsInstance analyticsLogger: AnalyticsLogger,
            @BindsInstance initialConfig: Config,
            @BindsInstance logger: Logger,
            @BindsInstance applicationContext: Context,
            @BindsInstance @Named(TEST_DISPATCHER_NAME) testDispatcher: CoroutineDispatcher?,
        ): BaseCriOrchestratorSingletonComponent
    }

    fun coroutineScope(): CoroutineScope

    fun coroutineDispatchers(): CoroutineDispatchers

    fun services(): Set<CriOrchestratorService>

    fun logger(): Logger

    fun analyticsLogger(): AnalyticsLogger

    fun authenticatedHttpClient(): GenericHttpClient
}
