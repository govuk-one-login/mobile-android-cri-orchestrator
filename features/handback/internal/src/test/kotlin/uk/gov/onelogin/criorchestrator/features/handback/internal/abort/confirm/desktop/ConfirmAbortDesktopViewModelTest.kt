package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmAbortDesktopViewModelTest {
    private val analytics = mock<HandbackAnalytics>()
    private val abortSession = StubAbortSession()
    private val viewModel by lazy {
        ConfirmAbortDesktopViewModel(
            analytics = analytics,
            abortSession = abortSession,
        )
    }

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
    fun `when screen starts, display state is set to display`() {
        viewModel.onScreenStart()

        assertEquals(ConfirmAbortState.Display, viewModel.state.value)
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
    fun `when continue is clicked, display state is set to loading`() {
        viewModel.onContinueClicked()

        assertEquals(ConfirmAbortState.Loading, viewModel.state.value)
    }

    @Test
    fun `given continue is clicked, when abort call successful, then emit navigation action to return to desktop`() =
        runTest {
            abortSession.result = AbortSession.Result.Success
            viewModel.actions.test {
                viewModel.onContinueClicked()
                assertEquals(
                    ConfirmAbortDesktopActions.NavigateToReturnToDesktop,
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given continue is clicked, when abort call unsuccessful, then emit nav action to unrecoverable error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Unrecoverable(exception = Exception("exception"))
            viewModel.actions.test {
                viewModel.onContinueClicked()
                assertEquals(
                    ConfirmAbortDesktopActions.NavigateToUnrecoverableError,
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given continue is clicked, when user is offline, then emit navigation action to offline error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Offline
            viewModel.actions.test {
                viewModel.onContinueClicked()
                assertEquals(
                    ConfirmAbortDesktopActions.NavigateToOfflineError,
                    awaitItem(),
                )
            }
        }
}
