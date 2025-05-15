package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmAbortMobileViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        ConfirmAbortMobileViewModel(
            analytics = analytics,
            sessionStore = FakeSessionStore(),
            abortSession = StubAbortSession(),
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ConfirmAbortToMobile,
                title = ConfirmAbortMobileConstants.titleId,
            )
    }

    @Test
    fun `when continue is clicked, send analytics`() {
        viewModel.onContinueToGovUk()

        verify(analytics)
            .trackButtonEvent(
                buttonText = ConfirmAbortMobileConstants.buttonId,
            )
    }
}
