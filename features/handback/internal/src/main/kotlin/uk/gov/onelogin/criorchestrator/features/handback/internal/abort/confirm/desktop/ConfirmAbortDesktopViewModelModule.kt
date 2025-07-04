package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

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
object ConfirmAbortDesktopViewModelModule {
    const val FACTORY_NAME = "ConfirmAbortDesktopWebViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: HandbackAnalytics,
        abortSession: AbortSession,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ConfirmAbortDesktopViewModel(
                    analytics = analytics,
                    abortSession = abortSession,
                )
            }
        }
}
