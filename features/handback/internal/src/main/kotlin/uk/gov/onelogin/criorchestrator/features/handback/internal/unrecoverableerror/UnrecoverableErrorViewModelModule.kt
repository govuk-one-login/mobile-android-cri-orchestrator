package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object UnrecoverableErrorViewModelModule {
    const val FACTORY_NAME = "UnrecoverableErrorViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(analytics: HandbackAnalytics): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                UnrecoverableErrorViewModel(analytics)
            }
        }
}
