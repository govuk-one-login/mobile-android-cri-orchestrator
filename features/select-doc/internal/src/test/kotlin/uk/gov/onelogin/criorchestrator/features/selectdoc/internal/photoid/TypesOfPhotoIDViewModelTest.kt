package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock
import java.time.LocalDate

@ExtendWith(MainDispatcherExtension::class)
class TypesOfPhotoIDViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private val earliestAcceptableExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(testClock())

    private val configStore = FakeConfigStore()
    private val viewModel by lazy {
        TypesOfPhotoIDViewModel(
            analytics = analyticsLogger,
            earliestAcceptableDrivingLicenceExpiryDate = earliestAcceptableExpiryDate,
            configStore = configStore,
        )
    }

    @Test
    fun `it emits the initial state`() =
        runTest {
            viewModel.state.test {
                assertEquals(
                    TypesOfPhotoIDState(
                        earliestExpiryDate = LocalDate.of(2025, 12, 26),
                        enableExpiredDrivingLicences = true,
                    ),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given expired driving licences are disabled, it emits the initial state`() =
        runTest {
            configStore.write(
                Config.Entry(
                    key = SdkConfigKey.EnableExpiredDrivingLicences,
                    value = Config.Value.BooleanValue(false),
                ),
            )
            viewModel.state.test {
                assertEquals(
                    TypesOfPhotoIDState(
                        earliestExpiryDate = LocalDate.of(2025, 12, 26),
                        enableExpiredDrivingLicences = false,
                    ),
                    awaitItem(),
                )
            }
        }
}
