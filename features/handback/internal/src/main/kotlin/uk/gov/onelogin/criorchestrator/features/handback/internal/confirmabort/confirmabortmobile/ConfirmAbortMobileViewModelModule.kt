package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortmobile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ConfirmAbortMobileViewModelModule {
    const val FACTORY_NAME = "ConfirmAbortToMobileWebViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        sessionStore: SessionStore,
        analytics: HandbackAnalytics,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ConfirmAbortMobileViewModel(
                    sessionStore,
                    analytics,
                )
            }
        }
}
