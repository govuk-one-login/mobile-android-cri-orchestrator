package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock
import java.time.LocalDate

@ExtendWith(MainDispatcherExtension::class)
class TypesOfPhotoIDViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private val earliestAcceptableExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(testClock())

    private val viewModel by lazy {
        TypesOfPhotoIDViewModel(
            analytics = analyticsLogger,
            earliestAcceptableDrivingLicenceExpiryDate = earliestAcceptableExpiryDate,
        )
    }

    @Test
    fun `it emits the initial state`() =
        runTest {
            viewModel.state.test {
                assertEquals(
                    TypesOfPhotoIDState(
                        earliestExpiryDate = LocalDate.of(2025, 12, 26),
                    ),
                    awaitItem(),
                )
            }
        }
}
