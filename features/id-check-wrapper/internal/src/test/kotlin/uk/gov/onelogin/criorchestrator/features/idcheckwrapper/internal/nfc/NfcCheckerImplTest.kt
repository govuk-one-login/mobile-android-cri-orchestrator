package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nfc

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker as IdCheckSdkNfcChecker

class NfcCheckerImplTest {
    private val configStore = FakeConfigStore()
    private val deviceNfcChecker = mock<IdCheckSdkNfcChecker>()
    val nfcChecker =
        NfcCheckerImpl(
            configStore = configStore,
            deviceNfcChecker = deviceNfcChecker,
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
        given(deviceNfcChecker.hasNfc()).willReturn(true)

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
        given(deviceNfcChecker.hasNfc()).willReturn(false)

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
        given(deviceNfcChecker.hasNfc()).willReturn(false)

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
        given(deviceNfcChecker.hasNfc()).willReturn(true)

        assertFalse(nfcChecker.hasNfc())
    }
}
