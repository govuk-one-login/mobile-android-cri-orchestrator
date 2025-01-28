package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ProveYourIdentityViewModelModule {
    const val FACTORY_NAME = "ProveYourIdentityViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analyticsLogger: AnalyticsLogger
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ProveYourIdentityViewModel(
                    analyticsLogger = analyticsLogger,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
}
