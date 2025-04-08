package uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc

import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.OptionConfigKey
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey.NfcAvailability.OPTION_AVAILABLE
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey.NfcAvailability.OPTION_DEVICE
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE

sealed interface NfcConfigKey {
    data object NfcAvailability :
        OptionConfigKey(
            name = "NFC availability",
            options =
                persistentListOf(
                    OPTION_DEVICE,
                    OPTION_AVAILABLE,
                    OPTION_NOT_AVAILABLE,
                ),
        ),
        NfcConfigKey {
        const val OPTION_DEVICE = "Use device setting"
        const val OPTION_AVAILABLE = "Available"
        const val OPTION_NOT_AVAILABLE = "Not available"
    }
}
