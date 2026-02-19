package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import uk.gov.idcheck.repositories.api.config.NfcAvailability
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

internal fun nfcAvailabilityFromConfigValue(value: String): NfcAvailability =
    when (value) {
        NfcConfigKey.NfcAvailability.OPTION_AVAILABLE -> NfcAvailability.Available
        NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE -> NfcAvailability.Unavailable
        else -> NfcAvailability.Device
    }
