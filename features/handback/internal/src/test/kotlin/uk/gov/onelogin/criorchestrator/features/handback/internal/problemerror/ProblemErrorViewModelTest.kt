package uk.gov.onelogin.criorchestrator.features.handback.internal.problemerror

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ProblemErrorViewModelTest {
    private val analyticsLogger = mock<HandbackAnalytics>()

    private val viewModel by lazy {
        ProblemErrorViewModel(
            analytics =
            analyticsLogger,
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = HandbackScreenId.ProblemError,
                title = R.string.handback_problemerror_title,
            )
    }

    @Test
    fun `when button is clicked, navigate to abort`() {
        runTest {
            viewModel.actions.test {
                viewModel.onButtonClick()

                assertEquals(ProblemErrorAction.NavigateToAbort, awaitItem())
            }
        }
    }
}
