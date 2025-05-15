package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.DisableBackButtonTest

@RunWith(AndroidJUnit4::class)
class ReturnToDesktopWebScreenDisableBackButtonTest : DisableBackButtonTest() {
    private val viewModel =
        ReturnToDesktopWebViewModel(
            analytics = mock(),
            requestAppReview = mock(),
        )

    @Before
    fun setup() {
        setContent {
            ReturnToDesktopWebScreen(
                viewModel = viewModel,
            )
        }
    }
}
