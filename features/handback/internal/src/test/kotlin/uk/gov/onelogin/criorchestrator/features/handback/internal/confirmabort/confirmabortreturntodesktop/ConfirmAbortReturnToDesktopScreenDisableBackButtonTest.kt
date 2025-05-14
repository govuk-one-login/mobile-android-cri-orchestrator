package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktop

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.DisableBackButtonTest

@RunWith(AndroidJUnit4::class)
class ConfirmAbortReturnToDesktopScreenDisableBackButtonTest : DisableBackButtonTest() {
    private val viewModel =
        ConfirmAbortReturnToDesktopViewModel(
            analytics = mock(),
        )

    @Before
    fun setup() {
        setContent {
            ConfirmAbortReturnToDesktopWebScreen(
                viewModel = viewModel,
            )
        }
    }
}
