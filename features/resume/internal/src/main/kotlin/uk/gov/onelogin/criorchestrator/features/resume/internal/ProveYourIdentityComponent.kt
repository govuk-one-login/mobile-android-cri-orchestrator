package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesTo(CriOrchestratorScope::class)
interface ProveYourIdentityComponent {
    @Named(ProveYourIdentityViewModelModule.FACTORY_NAME)
    fun proveIdentityViewModelFactory(): ViewModelProvider.Factory
}
