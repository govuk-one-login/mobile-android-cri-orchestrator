package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.GetActiveSessionUseCase
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.ResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ProveYourIdentityViewModelModule {
    const val FACTORY_NAME = "ProveYourIdentityViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analyticsLogger: AnalyticsLogger,
        resourceProvider: ResourceProvider,
        getActiveSessionUseCase: GetActiveSessionUseCase,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ProveYourIdentityViewModel(
                    analyticsLogger = analyticsLogger,
                    resourceProvider = resourceProvider,
                    getActiveSessionUseCase = getActiveSessionUseCase,
                )
            }
        }
}
