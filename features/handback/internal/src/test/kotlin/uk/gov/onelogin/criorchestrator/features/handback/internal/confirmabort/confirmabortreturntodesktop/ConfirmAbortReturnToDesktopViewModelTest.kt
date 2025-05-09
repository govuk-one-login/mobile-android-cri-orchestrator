package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktop

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import kotlin.test.Test

class ConfirmAbortReturnToDesktopViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        ConfirmAbortReturnToDesktopViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ConfirmAbortReturnToDesktop,
                title = ConfirmAbortReturnToDesktopConstants.titleId,
            )
    }
}
