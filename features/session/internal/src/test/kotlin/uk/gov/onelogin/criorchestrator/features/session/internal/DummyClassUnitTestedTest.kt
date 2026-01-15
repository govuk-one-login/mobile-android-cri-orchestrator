package uk.gov.onelogin.criorchestrator.features.session.internal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DummyClassUnitTestedTest {
    @Test
    fun `test add`() {
        assertEquals(2, DummyClassUnitTested().add(1, 1))
    }
}
