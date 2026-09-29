package uk.gov.onelogin.criorchestrator.features.handback.internal.unit.facescanlimitreached.desktop

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop.FaceScanLimitReachedDesktopConstants
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop.FaceScanLimitReachedDesktopViewModel
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class FaceScanLimitReachedDesktopViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        FaceScanLimitReachedDesktopViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends screen analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.FaceScanLimitReachedDesktop,
                title = FaceScanLimitReachedDesktopConstants.titleId,
            )
    }
}
