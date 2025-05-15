package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import kotlin.test.Test

class AbortedReturnToDesktopViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        AbortedReturnToDesktopViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ConfirmAbortReturnToDesktop,
                title = AbortedReturnToDesktopConstants.titleId,
            )
    }
}
