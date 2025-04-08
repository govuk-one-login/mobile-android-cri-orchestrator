package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.error.internal.R
import uk.gov.onelogin.criorchestrator.features.error.internal.analytics.ErrorAnalytics
import uk.gov.onelogin.criorchestrator.features.error.internal.analytics.ErrorScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class RecoverableErrorViewModelTest {
    private val analyticsLogger = mock<ErrorAnalytics>()

    private val viewModel by lazy {
        RecoverableErrorViewModel(
            analytics = analyticsLogger,
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = ErrorScreenId.RecoverableError,
                title = R.string.error_recoverableerror_title,
            )
    }

    @Test
    fun `when primary button is clicked, navigate back`() {
        runTest {
            viewModel.actions.test {
                viewModel.onButtonClick()

                assertEquals(RecoverableErrorAction.NavigateBack, awaitItem())
            }
        }
    }
}
