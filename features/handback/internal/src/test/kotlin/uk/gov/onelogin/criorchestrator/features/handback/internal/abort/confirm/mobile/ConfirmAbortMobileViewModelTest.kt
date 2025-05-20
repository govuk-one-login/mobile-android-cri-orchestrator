package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmAbortMobileViewModelTest {
    private val analytics = mock<HandbackAnalytics>()
    private val logger = SystemLogger()
    private val abortSession = StubAbortSession()
    private val redirectUri = "https://example-mam-uri"
    private val session =
        Session.createTestInstance(
            redirectUri = redirectUri,
        )
    private val sessionStore =
        FakeSessionStore(
            session = session,
        )

    private val viewModel =
        ConfirmAbortMobileViewModel(
            analytics = analytics,
            sessionStore = sessionStore,
            abortSession = abortSession,
            logger = logger,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ConfirmAbortMobile,
                title = ConfirmAbortMobileConstants.titleId,
            )
    }

    @Test
    fun `when screen starts, display state is set to display`() {
        viewModel.onScreenStart()

        assertEquals(ConfirmAbortState.Display, viewModel.state.value)
    }

    @Test
    fun `when continue to GOV UK is clicked, send analytics`() {
        viewModel.onContinueToGovUk()

        verify(analytics)
            .trackButtonEvent(
                buttonText = ConfirmAbortMobileConstants.buttonId,
            )
    }

    @Test
    fun `when continue to GOV UK is clicked, display state is set to loading`() {
        viewModel.onContinueToGovUk()

        assertEquals(ConfirmAbortState.Loading, viewModel.state.value)
    }

    @Test
    fun `given session store is empty, when continue is clicked, it navigates to unrecoverable error`() =
        runTest {
            sessionStore.write(null)
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    ConfirmAbortMobileAction.NavigateToUnrecoverableError,
                    awaitItem(),
                )
                assertTrue(logger.contains("Can't continue to GOV.UK - no redirect URI"))
            }
        }

    @Test
    fun `given DAD session (no redirect uri), when continue is clicked, it navigates to unrecoverable error`() =
        runTest {
            sessionStore.write(Session.createDesktopAppDesktopInstance())
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    ConfirmAbortMobileAction.NavigateToUnrecoverableError,
                    awaitItem(),
                )
                assertTrue(logger.contains("Can't continue to GOV.UK - no redirect URI"))
            }
        }

    @Test
    fun `given abort session will succeed, when continue is clicked, it navigates to govuk`() =
        runTest {
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    ConfirmAbortMobileAction.ContinueGovUk(redirectUri = redirectUri),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given abort session fails with offline error, when continue is clicked, it navigates to offline error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Offline
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    ConfirmAbortMobileAction.NavigateToOfflineError,
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given abort fails with unrecoverable error, when continue is clicked, it navigates to unrecoverable error`() =
        runTest {
            abortSession.result = AbortSession.Result.Error.Unrecoverable(exception = Exception("exception"))
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    ConfirmAbortMobileAction.NavigateToUnrecoverableError,
                    awaitItem(),
                )
            }
        }
}
