package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import kotlin.test.assertEquals

@ExtendWith(MainDispatcherExtension::class)
class ConfirmAbortMobileViewModelTest {
    private val analytics = mock<HandbackAnalytics>()
    private var sessionStore = FakeSessionStore()
    private val viewModel by lazy {
        ConfirmAbortMobileViewModel(
            analytics = analytics,
            sessionStore = sessionStore,
            abortSession = StubAbortSession(),
        )
    }

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
    fun `when continue to GOV UK is clicked, send analytics`() {
        viewModel.onContinueToGovUk()

        verify(analytics)
            .trackButtonEvent(
                buttonText = ConfirmAbortMobileConstants.buttonId,
            )
    }

    @Test
    fun `when continue to GOV UK is clicked, it navigates to AbortRedirectToMobileWebHolder screen`() =
        runTest {
            sessionStore = FakeSessionStore(Session.Companion.createMobileAppMobileInstance())
            viewModel.actions.test {
                viewModel.onContinueToGovUk()
                assertEquals(
                    ConfirmAbortMobileAction.ContinueGovUk(
                        redirectUri = "http://mam-redirect-uri",
                    ),
                    awaitItem(),
                )
            }
        }
}
