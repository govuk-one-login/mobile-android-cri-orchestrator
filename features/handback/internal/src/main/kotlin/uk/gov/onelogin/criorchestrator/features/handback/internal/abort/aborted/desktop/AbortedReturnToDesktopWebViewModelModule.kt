package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

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
object AbortedReturnToDesktopWebViewModelModule {
    const val FACTORY_NAME = "AbortedReturnToDesktopWebViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideViewModel(analytics: HandbackAnalytics): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                AbortedReturnToDesktopWebViewModel(
                    analytics = analytics,
                )
            }
        }
}
