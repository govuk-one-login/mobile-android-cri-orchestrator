package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import android.content.Context
import android.nfc.NfcManager
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object NfcBindings {
    @Provides
    fun provideNfcManager(context: Context): NfcManager = context.getSystemService(Context.NFC_SERVICE) as NfcManager
}
