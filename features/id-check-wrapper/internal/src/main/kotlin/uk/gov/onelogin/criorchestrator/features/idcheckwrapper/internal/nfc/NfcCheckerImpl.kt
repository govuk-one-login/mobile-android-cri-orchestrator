package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker as IdCheckSdkNfcChecker

class NfcCheckerImpl(
    private val configStore: ConfigStore,
    private val deviceNfcChecker: IdCheckSdkNfcChecker,
) : NfcChecker {
    override fun hasNfc(): Boolean =
        when (configStore.readSingle(NfcConfigKey.NfcAvailability).value) {
            NfcConfigKey.NfcAvailability.OPTION_AVAILABLE -> true
            NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE -> false
            else -> deviceNfcChecker.hasNfc()
        }
}
