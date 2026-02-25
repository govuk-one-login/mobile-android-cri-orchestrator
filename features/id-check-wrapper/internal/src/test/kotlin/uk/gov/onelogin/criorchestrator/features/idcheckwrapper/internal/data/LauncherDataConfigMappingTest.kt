package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.idcheck.repositories.api.config.NfcAvailability
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

class LauncherDataConfigMappingTest {
    @Test
    fun `given available config value, it returns Available`() {
        val result = nfcAvailabilityFromConfigValue(NfcConfigKey.NfcAvailability.OPTION_AVAILABLE)

        assertEquals(NfcAvailability.Available, result)
    }

    @Test
    fun `given not available config value, it returns Unavailable`() {
        val result = nfcAvailabilityFromConfigValue(NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE)

        assertEquals(NfcAvailability.Unavailable, result)
    }

    @Test
    fun `given device config value, it returns Device`() {
        val result = nfcAvailabilityFromConfigValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE)

        assertEquals(NfcAvailability.Device, result)
    }

    @Test
    fun `given unknown config value, it returns Device`() {
        val result = nfcAvailabilityFromConfigValue("unknown")

        assertEquals(NfcAvailability.Device, result)
    }
}
