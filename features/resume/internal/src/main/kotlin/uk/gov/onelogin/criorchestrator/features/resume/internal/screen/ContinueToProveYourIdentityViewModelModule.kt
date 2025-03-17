package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ContinueToProveYourIdentityViewModelModule {
    const val FACTORY_NAME = "ContinueToProveYourIdentityViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ContinueToProveYourIdentityViewModel()
            }
        }
}
