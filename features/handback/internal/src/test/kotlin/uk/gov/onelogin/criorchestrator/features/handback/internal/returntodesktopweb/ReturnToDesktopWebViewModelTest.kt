package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.RequestAppReview
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@ExtendWith(MainDispatcherExtension::class)
class ReturnToDesktopWebViewModelTest {
    private val analytics = mock<HandbackAnalytics>()
    private val requestAppReview = mock<RequestAppReview>()

    private val viewModel =
        ReturnToDesktopWebViewModel(
            analytics = analytics,
            requestAppReview = requestAppReview,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when screen starts, it triggers a review after two seconds`() =
        runTest {
            viewModel.onScreenStart()

            advanceTimeBy(2.seconds)
            verifyNoInteractions(requestAppReview)

            advanceTimeBy(1.milliseconds)
            verify(requestAppReview).invoke()
        }
}
