package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
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
