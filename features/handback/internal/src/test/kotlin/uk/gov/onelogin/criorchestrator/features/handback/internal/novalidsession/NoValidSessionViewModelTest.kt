package uk.gov.onelogin.criorchestrator.features.handback.internal.novalidsession

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId

class NoValidSessionViewModelTest {
    private val analyticsLogger = mock<HandbackAnalytics>()

    private val sut by lazy {
        NoValidSessionViewModel(analyticsLogger)
    }

    @Test
    fun `when screen starts it sends analytics`() {
        sut.onScreenStart()

        verify(analyticsLogger).trackScreen(
            id = HandbackScreenId.NoValidSessionError,
            title = NoValidSessionConstants.titleId,
        )
    }
}
