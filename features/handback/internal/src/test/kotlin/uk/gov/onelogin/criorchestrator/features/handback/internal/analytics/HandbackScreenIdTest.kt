package uk.gov.onelogin.criorchestrator.features.handback.internal.analytics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HandbackScreenIdTest {
    @Test
    fun `when screens are displayed, the IDs are set correctly`() {
        assertEquals("80069598-8c97-4789-96f8-8930fb633889", HandbackScreenId.UnrecoverableError.rawId)
        assertEquals("fff9c5fe-3eb2-45d4-a07a-fb6d21582c50", HandbackScreenId.ReturnToDesktopWeb.rawId)
        assertEquals("4a9fafaa-5359-4105-b223-a51d71df435f", HandbackScreenId.ReturnToMobileWeb.rawId)
        assertEquals("8d09e79f-f242-48c8-a456-4f302c23e6cb", HandbackScreenId.ConfirmAbortDesktop.rawId)
        assertEquals("8d09e79f-f242-48c8-a456-4f302c23e6cb", HandbackScreenId.ConfirmAbortReturnToDesktop.rawId)
    }
}
