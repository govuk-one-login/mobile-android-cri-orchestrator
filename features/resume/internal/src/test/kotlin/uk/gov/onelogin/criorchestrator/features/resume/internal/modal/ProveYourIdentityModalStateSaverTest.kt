package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import androidx.compose.runtime.saveable.SaverScope
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ProveYourIdentityModalStateSaverTest {
    private companion object {
        val CanSaveScope = SaverScope { true }
    }

    @Test
    fun `save and restore`() {
        val allowedToShow = true
        val state =
            ProveYourIdentityModalState(
                allowedToShow = allowedToShow,
            )

        val saved =
            with(CanSaveScope) {
                with(proveYourIdentityModalStateSaver) {
                    save(state)
                }
            }

        assertNotNull(saved)

        val restored = proveYourIdentityModalStateSaver.restore(saved!!)

        assertEquals(restored?.allowedToShow, allowedToShow)
    }
}
