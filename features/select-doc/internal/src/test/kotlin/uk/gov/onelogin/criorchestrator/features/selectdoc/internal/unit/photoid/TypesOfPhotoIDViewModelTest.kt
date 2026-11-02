package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.unit.photoid

import app.cash.turbine.test
import java.time.LocalDate
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDState
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock

@ExtendWith(MainDispatcherExtension::class)
class TypesOfPhotoIDViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private val earliestAcceptableExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(testClock())
    private lateinit var fakeSessionStore: FakeSessionStore
    private val configStore = FakeConfigStore()
    private val viewModel by lazy {
        TypesOfPhotoIDViewModel(
            analytics = analyticsLogger,
            earliestAcceptableDrivingLicenceExpiryDate = earliestAcceptableExpiryDate,
            configStore = configStore,
            sessionStore = fakeSessionStore,
        )
    }

    @Test
    fun `it emits the initial state with disableBrpJourney false`() = runTest {
        fakeSessionStore = FakeSessionStore(Session.createTestInstance(disableBrpJourney = false))
        viewModel.state.test {
            assertEquals(
                TypesOfPhotoIDState(
                    earliestExpiryDate = LocalDate.of(2025, 12, 26),
                    enableExpiredBrp = true,
                    disableBrpJourney = false,
                ),
                awaitItem(),
            )
        }
    }

    @Test
    fun `it emits the initial state with disableBrpJourney true`() = runTest {
        fakeSessionStore = FakeSessionStore(Session.createTestInstance(disableBrpJourney = true))
        viewModel.state.test {
            assertEquals(
                TypesOfPhotoIDState(
                    earliestExpiryDate = LocalDate.of(2025, 12, 26),
                    enableExpiredBrp = true,
                    disableBrpJourney = true,
                ),
                awaitItem(),
            )
        }
    }
}
