package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class ReturnToDesktopWebViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        ReturnToDesktopWebViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ReturnToDesktopWeb,
                title = ReturnToDesktopWebConstants.titleId,
            )
    }
}
