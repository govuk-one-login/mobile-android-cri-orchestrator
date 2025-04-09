package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.error.internal.analytics.ErrorAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object RecoverableErrorViewModelModule {
    const val FACTORY_NAME = "RecoverableErrorViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(analytics: ErrorAnalytics): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                RecoverableErrorViewModel(analytics)
            }
        }
}
