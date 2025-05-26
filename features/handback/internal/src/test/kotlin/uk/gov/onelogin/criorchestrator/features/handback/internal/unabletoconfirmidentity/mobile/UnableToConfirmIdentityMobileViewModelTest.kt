package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class UnableToConfirmIdentityMobileViewModelTest {
    private val analytics = mock<HandbackAnalytics>()
    private val abortSession = StubAbortSession()
    private val viewModel by lazy {
        UnableToConfirmIdentityMobileViewModel(
            analytics = analytics,
            abortSession = abortSession,
            sessionStore = FakeSessionStore(Session.createMobileAppMobileInstance()),
            logger = SystemLogger(),
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.UnableToConfirmIdentityMobile,
                title = UnableToConfirmIdentityMobileConstants.titleId,
            )
    }

    @Test
    fun `when screen starts, display state is set to display`() {
        viewModel.onScreenStart()

        assertEquals(ConfirmAbortState.Display, viewModel.state.value)
    }

    @Test
    fun `when continue is clicked, send analytics`() {
        viewModel.onContinueToGovUk()

        verify(analytics)
            .trackButtonEvent(
                buttonText = UnableToConfirmIdentityMobileConstants.buttonId,
            )
    }

    @Test
    fun `when continue is clicked, display state is set to loading`() {
        viewModel.onContinueToGovUk()

        assertEquals(ConfirmAbortState.Loading, viewModel.state.value)
    }

    @Test
    fun `given continue is clicked, when abort call successful, then emit navigation action to return to desktop`() =
        runTest {
            abortSession.result = AbortSession.Result.Success
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    UnableToConfirmIdentityMobileActions.ContinueGovUk(
                        redirectUri = "http://mam-redirect-uri?state=mock-state",
                    ),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given continue is clicked, when abort call unsuccessful, then emit nav action to unrecoverable error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Unrecoverable(exception = Exception("exception"))
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    UnableToConfirmIdentityMobileActions.NavigateToUnrecoverableError,
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given continue is clicked, when user is offline, then emit navigation action to offline error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Offline
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    UnableToConfirmIdentityMobileActions.NavigateToOfflineError,
                    awaitItem(),
                )
            }
        }
}
