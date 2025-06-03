package uk.gov.onelogin.criorchestrator.sdk.internal

import android.app.Activity
import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import uk.gov.onelogin.criorchestrator.features.config.internal.ConfigComponent
import uk.gov.onelogin.criorchestrator.features.session.internal.SessionComponent
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSessionComponent
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

/**
 * The real Dagger component that other component interfaces and modules will be merged into.
 */
@CompositionScope
@MergeComponent(
    CriOrchestratorScope::class,
    dependencies = [
        BaseCriOrchestratorSingletonComponent::class,
        ConfigComponent::class,
        SessionComponent::class,
        RefreshActiveSessionComponent::class,
    ],
)
interface BaseCriOrchestratorComponent {
    @MergeComponent.Factory
    interface Factory {
        @Suppress("LongParameterList")
        fun create(
            baseSingletonComponent: BaseCriOrchestratorSingletonComponent,
            configComponent: ConfigComponent,
            sessionComponent: SessionComponent,
            refreshActiveSessionComponent: RefreshActiveSessionComponent,
            @BindsInstance context: Context,
            @BindsInstance activity: Activity,
        ): BaseCriOrchestratorComponent
    }
}
