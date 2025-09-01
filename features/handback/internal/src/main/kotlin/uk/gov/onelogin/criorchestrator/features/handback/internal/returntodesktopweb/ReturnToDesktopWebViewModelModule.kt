package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.RequestAppReview
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object ReturnToDesktopWebViewModelModule {
    const val FACTORY_NAME = "ReturnToDesktopViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: HandbackAnalytics,
        requestAppReview: RequestAppReview,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ReturnToDesktopWebViewModel(
                    analytics = analytics,
                    requestAppReview = requestAppReview,
                )
            }
        }
}
