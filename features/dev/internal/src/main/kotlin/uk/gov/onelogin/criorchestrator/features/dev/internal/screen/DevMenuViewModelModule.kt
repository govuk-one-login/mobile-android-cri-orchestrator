package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object DevMenuViewModelModule {
    const val FACTORY_NAME = "DevMenuViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(configStore: ConfigStore): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                DevMenuViewModel(
                    configStore = configStore,
                )
            }
        }
}
