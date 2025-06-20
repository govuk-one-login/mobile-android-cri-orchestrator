package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
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
