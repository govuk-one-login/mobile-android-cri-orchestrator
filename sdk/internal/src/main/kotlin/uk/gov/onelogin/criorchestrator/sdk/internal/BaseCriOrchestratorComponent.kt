package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import uk.gov.onelogin.criorchestrator.features.config.internal.ConfigComponent
import uk.gov.onelogin.criorchestrator.features.session.internal.SessionComponent
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
    ],
)
interface BaseCriOrchestratorComponent {
    @MergeComponent.Factory
    interface Factory {
        fun create(
            baseSingletonComponent: BaseCriOrchestratorSingletonComponent,
            configComponent: ConfigComponent,
            sessionComponent: SessionComponent,
            @BindsInstance context: Context,
        ): BaseCriOrchestratorComponent
    }
}
