package uk.gov.onelogin.criorchestrator.features.session.internal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.libraries.testing.tags.IntegrationTest

@IntegrationTest
class DummyClassIntegrationTestedTest {
    @Test
    fun `test add`() {
        assertEquals(2, DummyClassIntegrationTested().add(1, 1))
    }
}
