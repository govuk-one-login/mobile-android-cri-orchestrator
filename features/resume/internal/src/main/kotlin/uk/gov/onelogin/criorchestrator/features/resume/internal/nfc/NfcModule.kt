package uk.gov.onelogin.criorchestrator.features.resume.internal.nfc

import android.content.Context
import android.nfc.NfcManager
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcCheckerImpl
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
object NfcModule {
    @Provides
    @ActivityScope
    fun providesNfcManager(context: Context): NfcManager = context.getSystemService(Context.NFC_SERVICE) as NfcManager

    @Provides
    @ActivityScope
    fun provideNfcChecker(nfcManager: NfcManager): NfcChecker = NfcCheckerImpl(nfcManager)
}
