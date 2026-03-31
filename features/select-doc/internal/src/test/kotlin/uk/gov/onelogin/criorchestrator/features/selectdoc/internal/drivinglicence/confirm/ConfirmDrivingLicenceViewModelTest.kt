package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.confirm

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
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock
import java.time.LocalDate

@ExtendWith(MainDispatcherExtension::class)
class ConfirmDrivingLicenceViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private val earliestAcceptableExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(testClock())

    private val viewModel by lazy {
        ConfirmDrivingLicenceViewModel(
            analytics = analyticsLogger,
            earliestAcceptableExpiryDate = earliestAcceptableExpiryDate,
        )
    }

    @Test
    fun `it emits an initial state`() =
        runTest {
            viewModel.state.test {
                assertEquals(
                    ConfirmDrivingLicenceState(
                        earliestExpiryDate = LocalDate.of(2025, 12, 26),
                    ),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.ConfirmDrivingLicence,
                title = R.string.confirmdocument_drivinglicence_title,
            )
    }

    @Test
    fun `when confirm is pressed, send analytics`() =
        runTest {
            viewModel.action.test {
                viewModel.onConfirmClick()
                verify(analyticsLogger)
                    .trackButtonEvent(
                        buttonText = R.string.confirmdocument_confirmbutton,
                    )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when confirm is pressed, navigatae to SyncIdCheck screen`() =
        runTest {
            viewModel.action.test {
                viewModel.onConfirmClick()
                verify(analyticsLogger)
                    .trackButtonEvent(
                        buttonText = R.string.confirmdocument_confirmbutton,
                    )
                assertEquals(ConfirmDrivingLicenceAction.NavigateToSyncIdCheck, awaitItem())
            }
        }
}
