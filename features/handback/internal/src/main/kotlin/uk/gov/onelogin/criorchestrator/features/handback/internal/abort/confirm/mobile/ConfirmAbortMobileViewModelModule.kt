package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ConfirmAbortMobileViewModelModule {
    const val FACTORY_NAME = "ConfirmAbortMobileWebViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        sessionStore: SessionStore,
        analytics: HandbackAnalytics,
        abortSession: AbortSession,
        logger: Logger,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ConfirmAbortMobileViewModel(
                    sessionStore = sessionStore,
                    analytics = analytics,
                    abortSession = abortSession,
                    logger = logger,
                )
            }
        }
}
