package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.ConfirmDocumentScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmPassportViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()

    private val viewModel by lazy {
        ConfirmPassportViewModel(
            analytics =
            analyticsLogger,
        )
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = ConfirmDocumentScreenId.ConfirmPassport,
                title = R.string.confirmdocument_passport_title,
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
                assertEquals(ConfirmPassportAction.NavigateToSyncIdCheck, awaitItem())
            }
        }
}
