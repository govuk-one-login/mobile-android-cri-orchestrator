package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class AbortSessionUnitTest {
    val abortSession =
        AbortSessionImpl(
            sessionStore = mock(),
            abortSessionApi = mock(),
            logger = mock(),
        )

    @Test
    fun `test add`() {
        assertEquals(2, abortSession.addUnitTested(1, 1))
    }
}
