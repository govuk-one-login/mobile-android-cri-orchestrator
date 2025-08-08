package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ReturnToMobileWebViewModelTest {
    private val analytics = mock<HandbackAnalytics>()

    private val viewModel =
        ReturnToMobileWebViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics)
            .trackScreen(
                id = HandbackScreenId.ReturnToMobileWeb,
                title = ReturnToMobileWebConstants.titleId,
            )
    }
}
