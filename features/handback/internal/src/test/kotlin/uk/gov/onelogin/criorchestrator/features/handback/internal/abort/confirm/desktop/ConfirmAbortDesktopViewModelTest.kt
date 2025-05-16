package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileAction
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import kotlin.test.assertEquals

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
    fun `when continue is clicked, send analytics`() {
        viewModel.onContinueClicked()

        verify(analytics)
            .trackButtonEvent(
                buttonText = ConfirmAbortDesktopConstants.buttonId,
            )
    }

    @Test
    fun `given continue is clicked, when abort call successful, then emit navigation action to return to desktop`() =
        runTest {
            abortSession.result = AbortSession.Result.Success
            viewModel.actions.test {
                viewModel.onContinueClicked()
                Assertions.assertEquals(
                    ConfirmAbortDesktopActions.NavigateToReturnToDesktop,
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given continue is clicked, when abort call unsuccessful, then emit navigation action to unrecoverable error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Unrecoverable(exception = Exception("exception"))
            viewModel.actions.test {
                viewModel.onContinueClicked()
                Assertions.assertEquals(
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
                Assertions.assertEquals(
                    ConfirmAbortDesktopActions.NavigateToOfflineError,
                    awaitItem(),
                )
            }
        }

}
