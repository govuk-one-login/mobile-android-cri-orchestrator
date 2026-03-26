package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.util.date

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class FormatGdsDateTest {
    @Test
    fun `formats date in GDS style`() {
        val date = LocalDate.of(2025, 12, 26)

        val result = date.formatGdsDate()

        assertEquals("26 December 2025", result)
    }
}
