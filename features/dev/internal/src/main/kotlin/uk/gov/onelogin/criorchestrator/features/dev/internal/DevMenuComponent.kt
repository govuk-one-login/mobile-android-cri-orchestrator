package uk.gov.onelogin.criorchestrator.features.dev.internal

import androidx.lifecycle.ViewModelProvider
import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuViewModelModule
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@ActivityScope
@ContributesTo(CriOrchestratorScope::class)
interface DevMenuComponent {
    @Named(DevMenuViewModelModule.FACTORY_NAME)
    fun devMenuViewModelFactory(): ViewModelProvider.Factory
}
