package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionAbortedOrUnavailable
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object AbortModalViewModelModule {
    const val FACTORY_NAME = "AbortModalViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideViewModel(isSessionAbortedOrUnavailable: IsSessionAbortedOrUnavailable): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                AbortModalViewModel(
                    isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
                )
            }
        }
}
