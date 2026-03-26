package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock
import java.time.LocalDate

class EarliestAcceptableDrivingLicenceExpiryDateTest {
    private val clock =
        testClock(
            year = 2026,
            month = 3,
            dayOfMonth = 26,
        )

    @Test
    fun `returns date 90 days before the given date`() {
        val earliestAcceptableDrivingLicenceExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(clock)

        val result = earliestAcceptableDrivingLicenceExpiryDate()

        assertEquals(LocalDate.of(2025, 12, 26), result)
    }
}
