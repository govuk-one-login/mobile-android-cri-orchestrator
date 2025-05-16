package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import kotlin.test.Test

class AbortedReturnToDesktopWebViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        AbortedReturnToDesktopWebViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.AbortedReturnToDesktopWeb,
                title = AbortedReturnToDesktopWebConstants.titleId,
            )
    }
}
