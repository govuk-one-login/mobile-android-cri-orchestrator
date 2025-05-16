package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubIsSessionAbortedOrUnavailable

class AbortModalViewModelTest {
    private val isSessionAbortedOrUnavailable = StubIsSessionAbortedOrUnavailable(false)
    private val viewModel =
        AbortModalViewModel(
            isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
        )

    @Test
    fun `view model returns correct value from is aborted check when session is updated`() =
        runTest {
            viewModel.isAborted.test {
                assertFalse(awaitItem())
                isSessionAbortedOrUnavailable.state.value = true
                assertTrue(awaitItem())
            }
        }
}
