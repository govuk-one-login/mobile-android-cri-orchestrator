package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortreturntodesktopweb

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import kotlin.test.Test

class ConfirmAbortReturnToDesktopWebViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        ConfirmAbortReturnToDesktopWebViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ConfirmAbortReturnToDesktopWeb,
                title = ConfirmAbortReturnToDesktopWebConstants.titleId,
            )
    }
}
