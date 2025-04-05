package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmBrpViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()

    private val viewModel by lazy {
        ConfirmBrpViewModel(
            analytics = analyticsLogger,
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.ConfirmBrp,
                title = R.string.confirmdocument_brp_title,
            )
    }

    @Test
    fun `when confirm is pressed, send analytics and `() =
        runTest {
            viewModel.action.test {
                viewModel.onPrimary()
                verify(analyticsLogger)
                    .trackButtonEvent(
                        buttonText = R.string.confirmdocument_confirmbutton,
                    )
                assertEquals(ConfirmBrpAction.NavigateToSyncIdCheck, awaitItem())
            }
        }
}
