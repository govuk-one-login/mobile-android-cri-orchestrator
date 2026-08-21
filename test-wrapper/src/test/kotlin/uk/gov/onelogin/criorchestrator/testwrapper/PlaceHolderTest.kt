package uk.gov.onelogin.criorchestrator.testwrapper

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Add dummy test so sonar can provide overall test coverage estimation
 */
class PlaceHolderTest {
    @Test
    fun verifyDouble() {
        val sut = PlaceHolder()
        assertEquals(4, sut.double(2))
    }
}
