package uk.gov.onelogin.criorchestrator.features.resume.internal.nfc

import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import javax.inject.Inject

internal class ConfigurableNfcChecker @Inject constructor(
    private val deviceNfcChecker: NfcChecker,
    private val configStore: ConfigStore,
): NfcChecker {
    override fun hasNfc(): Boolean {
        if(configStore.readSingle(NfcConfigKey.StubNcfCheck).value) {
            return configStore.readSingle(NfcConfigKey.IsNfcAvailable).value
        }

        return deviceNfcChecker.hasNfc()
    }
}