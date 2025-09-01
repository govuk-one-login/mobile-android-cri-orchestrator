package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object ConfirmBrpViewModelModule {
    const val FACTORY_NAME = "ConfirmBrpViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(analytics: SelectDocAnalytics): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ConfirmBrpViewModel(
                    analytics,
                )
            }
        }
}
