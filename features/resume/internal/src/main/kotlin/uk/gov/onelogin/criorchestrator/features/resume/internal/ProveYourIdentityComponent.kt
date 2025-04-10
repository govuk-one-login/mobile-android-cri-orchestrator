package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModelProvider
import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@CompositionScope
@ContributesTo(CriOrchestratorScope::class)
interface ProveYourIdentityComponent {
    @Named(ProveYourIdentityViewModelModule.FACTORY_NAME)
    fun proveIdentityViewModelFactory(): ViewModelProvider.Factory
}
