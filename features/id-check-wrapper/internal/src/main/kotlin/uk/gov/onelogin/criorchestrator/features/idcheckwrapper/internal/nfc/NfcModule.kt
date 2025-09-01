package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import android.content.Context
import android.nfc.NfcManager
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcCheckerImpl as IdCheckSdkNfcCheckerImpl

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object NfcModule {
    @Provides
    @SingleIn(CriOrchestratorScope::class)
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
