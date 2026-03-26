package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class OldestDrivingLicenceExpiryDateTest {
    @Test
    fun `returns date 90 days before the given date`() {
        val oldestDrivingLicenceExpiryDate = OldestDrivingLicenceExpiryDate { LocalDate.of(2026, 3, 26) }

        val result = oldestDrivingLicenceExpiryDate()

        assertEquals(LocalDate.of(2025, 12, 26), result)
    }
}
