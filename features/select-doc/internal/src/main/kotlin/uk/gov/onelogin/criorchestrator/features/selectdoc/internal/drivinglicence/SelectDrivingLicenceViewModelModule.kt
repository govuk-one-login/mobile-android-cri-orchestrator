package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object SelectDrivingLicenceViewModelModule {
    const val FACTORY_NAME = "SelectDrivingLicenceViewModelModuleFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: SelectDocumentAnalytics,
        nfcChecker: NfcChecker,
        configStore: ConfigStore,
        ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                SelectDrivingLicenceViewModel(
                    analytics,
                    nfcChecker,
                    configStore,
                )
            }
        }
}
