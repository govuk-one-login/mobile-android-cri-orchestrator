package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object SyncIdCheckViewModelModule {
    const val FACTORY_NAME = "SyncIdCheckViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(launcherDataReader: LauncherDataReader): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                SyncIdCheckViewModel(
                    launcherDataReader = launcherDataReader,
                )
            }
        }
}
