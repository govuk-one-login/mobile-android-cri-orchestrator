package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object SelectDrivingLicenceViewModelModule {
    const val FACTORY_NAME = "SelectDrivingLicenceViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: SelectDocAnalytics,
        nfcChecker: NfcChecker,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                SelectDrivingLicenceViewModel(
                    analytics,
                    nfcChecker,
                )
            }
        }
}
