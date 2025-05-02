package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import android.content.Context
import android.nfc.NfcManager
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.libraries.di.CompositionScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcCheckerImpl as IdCheckSdkNfcCheckerImpl

@Module
@ContributesTo(CriOrchestratorScope::class)
object NfcModule {
    @Provides
    @CompositionScope
    fun provideNfcChecker(
        context: Context,
        configStore: ConfigStore,
    ): NfcChecker {
        val nfcManager = context.getSystemService(Context.NFC_SERVICE) as NfcManager

        val idCheckSdkNfcCheckerImpl = IdCheckSdkNfcCheckerImpl(nfcManager)

        return NfcCheckerImpl(
            configStore = configStore,
            deviceNfcChecker = idCheckSdkNfcCheckerImpl,
        )
    }
}
