package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object ContinueToProveYourIdentityViewModelModule {
    const val FACTORY_NAME = "ContinueToProveYourIdentityViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        analytics: ResumeAnalytics,
        nfcChecker: NfcChecker,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ContinueToProveYourIdentityViewModel(
                    analytics = analytics,
                    nfcChecker = nfcChecker,
                )
            }
        }
}
