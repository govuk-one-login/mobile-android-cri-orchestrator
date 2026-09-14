package uk.gov.onelogin.criorchestrator.testwrapper.unit.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.testwrapper.DummyClass

/** Test that solely exists to trigger the sonar code coverage calculation **/
class DummyTest {
    @Test
    fun verifyDouble() {
        assertEquals(4, DummyClass().double(2))
    }
}
