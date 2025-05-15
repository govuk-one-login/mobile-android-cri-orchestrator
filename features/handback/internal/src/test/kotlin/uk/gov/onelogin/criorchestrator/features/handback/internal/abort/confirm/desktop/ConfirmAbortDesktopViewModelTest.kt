package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import kotlin.test.assertEquals

@ExtendWith(MainDispatcherExtension::class)
class ConfirmAbortDesktopViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        ConfirmAbortDesktopViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ConfirmAbortDesktop,
                title = ConfirmAbortDesktopConstants.titleId,
            )
    }

    @Test
    fun `when continue is clicked, send analytics`() {
        viewModel.onContinueClicked()

        verify(analytics)
            .trackButtonEvent(
                buttonText = ConfirmAbortDesktopConstants.buttonId,
            )
    }

    @Test
    fun `when continue is clicked, it navigates to ReturnToDesktopWeb screen`() =
        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked()
                assertEquals(ConfirmAbortDesktopActions.NavigateToReturnToDesktop, awaitItem())
            }
        }
}
