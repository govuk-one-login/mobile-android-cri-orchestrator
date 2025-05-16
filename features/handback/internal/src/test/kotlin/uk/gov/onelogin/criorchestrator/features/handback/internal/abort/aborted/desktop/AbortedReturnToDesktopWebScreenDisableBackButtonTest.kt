package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.DisableBackButtonTest

@RunWith(AndroidJUnit4::class)
class AbortedReturnToDesktopWebScreenDisableBackButtonTest : DisableBackButtonTest() {
    private val viewModel =
        AbortedReturnToDesktopWebViewModel(
            analytics = mock(),
        )

    @Before
    fun setup() {
        setContent {
            AbortedReturnToDesktopWebScreen(
                viewModel = viewModel,
            )
        }
    }
}
