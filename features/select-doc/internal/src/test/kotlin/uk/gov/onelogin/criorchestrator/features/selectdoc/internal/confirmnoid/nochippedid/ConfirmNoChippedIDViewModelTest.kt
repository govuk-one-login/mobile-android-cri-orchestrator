package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmNoChippedIDViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()

    private val viewModel by lazy {
        ConfirmNoChippedIDViewModel(
            analytics = analyticsLogger,
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.ConfirmNoChippedID,
                title = R.string.confirm_nochippedid_title,
            )
    }

    @Test
    fun `when confirm is pressed, send analytics`() =
        runTest {
            viewModel.action.test {
                viewModel.onConfirmClick()
                verify(analyticsLogger)
                    .trackButtonEvent(
                        buttonText = R.string.confirm_nochippedid_confirmbutton,
                    )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when confirm is pressed, it navigates to Confirm Abort screen`() =
        runTest {
            viewModel.action.test {
                viewModel.onConfirmClick()
                verify(analyticsLogger)
                    .trackButtonEvent(
                        buttonText = R.string.confirm_nochippedid_confirmbutton,
                    )
                assertEquals(ConfirmNoChippedIDAction.NavigateToConfirmAbort, awaitItem())
            }
        }
}
