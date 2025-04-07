package uk.gov.onelogin.criorchestrator.features.resume.internal.nfc

import android.content.Context
import android.nfc.NfcManager
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcCheckerImpl
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
object NfcModule {
    @Provides
    @ActivityScope
    fun provideNfcChecker(
        context: Context,
        configStore: ConfigStore,
    ): NfcChecker = ConfigurableNfcChecker(
        deviceNfcChecker = NfcCheckerImpl(
            context.getSystemService(Context.NFC_SERVICE) as NfcManager
        ),
        configStore = configStore
    )
}
