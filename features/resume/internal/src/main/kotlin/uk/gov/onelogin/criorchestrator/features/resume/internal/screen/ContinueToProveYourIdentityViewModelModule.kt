package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import android.nfc.NfcManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcCheckerImpl
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
@ContributesTo(CriOrchestratorScope::class)
object ContinueToProveYourIdentityViewModelModule {
    const val FACTORY_NAME = "ContinueToProveYourIdentityViewModelFactory"

    @Provides
    @ActivityScope
    fun providesNfcManager(context: Context): NfcManager = context.getSystemService(Context.NFC_SERVICE) as NfcManager

    @Provides
    @ActivityScope
    fun provideNfcChecker(nfcManager: NfcManager): NfcChecker = NfcCheckerImpl(nfcManager)

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
