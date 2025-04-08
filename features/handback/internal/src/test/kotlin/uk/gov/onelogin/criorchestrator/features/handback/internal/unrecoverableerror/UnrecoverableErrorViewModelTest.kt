package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

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
class UnrecoverableErrorViewModelTest {
    private val analyticsLogger = mock<HandbackAnalytics>()

    private val viewModel by lazy {
        UnrecoverableErrorViewModel(
            analytics = analyticsLogger,
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = HandbackScreenId.UnrecoverableError,
                title = R.string.handback_unrecoverableerror_title,
            )
    }

    @Test
    fun `when button is clicked, navigate to abort`() {
        runTest {
            viewModel.actions.test {
                viewModel.onButtonClick()

                assertEquals(UnrecoverableErrorAction.NavigateToAbort, awaitItem())
            }
        }
    }
}
