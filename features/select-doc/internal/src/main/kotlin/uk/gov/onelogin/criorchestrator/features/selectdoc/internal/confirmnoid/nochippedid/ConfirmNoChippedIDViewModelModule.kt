package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ConfirmNoChippedIDViewModelModule {
    const val FACTORY_NAME = "ConfirmNoChippedIDViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: SelectDocAnalytics,
        sessionStore: SessionStore,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ConfirmNoChippedIDViewModel(
                    analytics = analytics,
                    sessionStore = sessionStore,
                )
            }
        }
}
