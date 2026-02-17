package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import android.nfc.NfcManager
import dev.zacsweers.metro.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class NfcCheckerImpl(
    private val configStore: ConfigStore,
    private val nfcManager: NfcManager,
) : NfcChecker {
    override fun hasNfc(): Boolean =
        when (configStore.readSingle(NfcConfigKey.NfcAvailability).value) {
            NfcConfigKey.NfcAvailability.OPTION_AVAILABLE -> true
            NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE -> false
            else -> nfcManager.defaultAdapter != null
        }
}
