package uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc

import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigKey

sealed interface NfcConfigKey {
    data object IsNfcAvailable :
        ConfigKey<Config.Value.BooleanValue>(
            name = "NFC Available",
        ),
        NfcConfigKey

    data object StubNcfCheck :
        ConfigKey<Config.Value.BooleanValue>(
            name = "Stub NFC Check",
        ),
        NfcConfigKey
}
