package uk.gov.onelogin.criorchestrator.features.dev.internal

import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuViewModelModule
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesTo(CriOrchestratorScope::class)
interface DevMenuComponent {
    @Named(DevMenuViewModelModule.FACTORY_NAME)
    fun devMenuViewModelFactory(): ViewModelProvider.Factory
}
