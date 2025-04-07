package uk.gov.onelogin.criorchestrator.features.resume.internal.nfc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker

class ConfigurableNfcCheckerTest {

    private val deviceNfcChecker = mock<NfcChecker>()
    private val configurableNfcChecker = ConfigurableNfcChecker(
        deviceNfcChecker = deviceNfcChecker,
        configStore =
    )

    @Test
    fun `given

}