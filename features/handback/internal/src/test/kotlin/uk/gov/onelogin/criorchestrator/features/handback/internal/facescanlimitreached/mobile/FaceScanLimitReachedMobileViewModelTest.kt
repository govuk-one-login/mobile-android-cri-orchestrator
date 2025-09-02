package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class FaceScanLimitReachedMobileViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        FaceScanLimitReachedMobileViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends screen analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.FaceScanLimitReachedMobile,
                title = FaceScanLimitReachedMobileConstants.titleId,
            )
    }

    @Test
    fun `onButtonClick() sends button analytics event`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.FaceScanLimitReachedMobile,
                title = FaceScanLimitReachedMobileConstants.titleId,
            )
    }
}
