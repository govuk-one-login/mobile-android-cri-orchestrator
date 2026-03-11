package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import android.nfc.NfcManager
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

class NfcCheckerImplTest {
    private val configStore = FakeConfigStore()
    private val nfcManager = mock<NfcManager>()
    val nfcChecker =
        NfcCheckerImpl(
            configStore = configStore,
            nfcManager = nfcManager,
        )

    @Test
    fun `given nfc is configured as device and device has nfc, it returns true`() {
        configStore.write(
            entry =
                Config.Entry(
                    NfcConfigKey.NfcAvailability,
                    Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
                ),
        )
        givenDeviceNfc(available = true)

        assertTrue(nfcChecker.hasNfc())
    }

    @Test
    fun `given nfc is configured as device and device has no nfc, it returns false`() {
        configStore.write(
            entry =
                Config.Entry(
                    NfcConfigKey.NfcAvailability,
                    Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
                ),
        )
        givenDeviceNfc(available = false)

        assertFalse(nfcChecker.hasNfc())
    }

    @Test
    fun `given nfc is configured as available despite device not having nfc, it returns true`() {
        configStore.write(
            entry =
                Config.Entry(
                    NfcConfigKey.NfcAvailability,
                    Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_AVAILABLE),
                ),
        )
        givenDeviceNfc(available = false)

        assertTrue(nfcChecker.hasNfc())
    }

    @Test
    fun `given nfc is configured as not available despite device having nfc, it returns false`() {
        configStore.write(
            entry =
                Config.Entry(
                    NfcConfigKey.NfcAvailability,
                    Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE),
                ),
        )
        givenDeviceNfc(available = true)

        assertFalse(nfcChecker.hasNfc())
    }

    private fun givenDeviceNfc(available: Boolean) =
        given(nfcManager.defaultAdapter).willReturn(
            if (available) mock() else null,
        )
}
