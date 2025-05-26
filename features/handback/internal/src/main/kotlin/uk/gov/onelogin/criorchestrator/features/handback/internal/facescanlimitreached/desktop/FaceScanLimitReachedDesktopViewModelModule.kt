package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object FaceScanLimitReachedDesktopViewModelModule {
    const val FACTORY_NAME = "FaceScanLimitReachedDesktopViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: HandbackAnalytics,
        abortSession: AbortSession,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                FaceScanLimitReachedDesktopViewModel(
                    analytics = analytics,
                    abortSession = abortSession,
                )
            }
        }
}
