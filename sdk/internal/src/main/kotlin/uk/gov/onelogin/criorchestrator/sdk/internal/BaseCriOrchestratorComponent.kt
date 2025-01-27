package uk.gov.onelogin.criorchestrator.sdk.internal

import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

/**
 * The real Dagger component that other component interfaces and modules will be merged into.
 */
@ActivityScope
@MergeComponent(CriOrchestratorScope::class)
interface BaseCriOrchestratorComponent {
    @MergeComponent.Factory
    interface Factory {
        fun create(
            @BindsInstance authenticatedHttpClient: GenericHttpClient,
            @BindsInstance analyticsLogger: AnalyticsLogger,
        ): BaseCriOrchestratorComponent
    }
}
