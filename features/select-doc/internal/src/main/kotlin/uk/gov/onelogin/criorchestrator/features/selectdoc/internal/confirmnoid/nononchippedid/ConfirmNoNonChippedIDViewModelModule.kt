package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object ConfirmNoNonChippedIDViewModelModule {
    const val FACTORY_NAME = "ConfirmNoNonChippedIDViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: SelectDocAnalytics,
        getJourneyType: GetJourneyType,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ConfirmNoNonChippedIDViewModel(
                    analytics = analytics,
                    getJourneyType = getJourneyType,
                )
            }
        }
}
