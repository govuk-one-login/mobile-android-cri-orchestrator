package uk.gov.onelogin.criorchestrator.features.handback.internal.analytics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HandbackScreenIdTest {
    @Test
    fun `when screens are displayed, the IDs are set correctly`() {
        assertEquals(HandbackScreenId.UnrecoverableError.rawId, "80069598-8c97-4789-96f8-8930fb633889")
        assertEquals(HandbackScreenId.ReturnToMobileWeb.rawId, "4a9fafaa-5359-4105-b223-a51d71df435f")
    }
}
