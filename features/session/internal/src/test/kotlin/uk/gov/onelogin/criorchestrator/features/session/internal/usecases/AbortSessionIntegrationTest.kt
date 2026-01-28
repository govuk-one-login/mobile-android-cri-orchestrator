package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.libraries.testing.tags.IntegrationTest

@IntegrationTest
class AbortSessionIntegrationTest {
    val abortSession =
        AbortSessionImpl(
            sessionStore = mock(),
            abortSessionApi = mock(),
            logger = mock(),
        )

    @Test
    fun `test add`() {
        assertEquals(2, abortSession.addIntegrationTested(1, 1))
    }
}
