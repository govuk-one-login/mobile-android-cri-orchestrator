package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.unit.confirmnoid.nononchippedid

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
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDState
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDViewModel
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.ConfirmNoNonChippedIDAction
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.ConfirmNoNonChippedIDState
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.ConfirmNoNonChippedIDViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmNoNonChippedIDViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private val fakeSessionStoreDisableBrp = FakeSessionStore(Session.createTestInstance(disableBrpJourney = true))
    private val fakeSessionStoreDefault = FakeSessionStore(Session.createTestInstance())

    @Test
    fun `when screen starts, it sends analytics`() {
        val viewModel =
            ConfirmNoNonChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType = StubGetJourneyType(),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.ConfirmNoNonChippedID,
                title = R.string.confirm_nononchippedid_title,
            )
    }

    @Test
    fun `when confirm is pressed, it sends analytics`() = runTest {
        val viewModel =
            ConfirmNoNonChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType = StubGetJourneyType(),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.action.test {
            viewModel.onConfirmClick()
            verify(analyticsLogger)
                .trackButtonEvent(
                    buttonText = R.string.confirm_nononchippedid_confirmbutton,
                )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when confirm is pressed and journey is MAM, it navigates to Confirm Abort Mobile screen`() = runTest {
        val viewModel =
            ConfirmNoNonChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.MobileAppMobile("https://example.com"),
                    ),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.action.test {
            viewModel.onConfirmClick()
            verify(analyticsLogger)
                .trackButtonEvent(
                    buttonText = R.string.confirm_nononchippedid_confirmbutton,
                )
            assertEquals(ConfirmNoNonChippedIDAction.NavigateToConfirmAbortMobile, awaitItem())
        }
    }

    @Test
    fun `when confirm is pressed and journey is DAD, it navigates to Confirm Abort Desktop screen`() = runTest {
        val viewModel =
            ConfirmNoNonChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.DesktopAppDesktop,
                    ),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.action.test {
            viewModel.onConfirmClick()
            verify(analyticsLogger)
                .trackButtonEvent(
                    buttonText = R.string.confirm_nononchippedid_confirmbutton,
                )
            assertEquals(ConfirmNoNonChippedIDAction.NavigateToConfirmAbortDesktop, awaitItem())
        }
    }

    @Test
    fun `when stored session does not contain disableBrpJourney flag it is not set in state`() = runTest {
        val viewModel =
            ConfirmNoNonChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType = StubGetJourneyType(),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.state.test {
            assertEquals(ConfirmNoNonChippedIDState(disableBrpJourney = false), awaitItem())
        }
    }

    @Test
    fun `when stored session does contains disableBrpJourney flag it is set in state`() = runTest {
        val viewModel =
            ConfirmNoNonChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType = StubGetJourneyType(),
                sessionStore = fakeSessionStoreDisableBrp,
            )

        viewModel.state.test {
            assertEquals(ConfirmNoNonChippedIDState(disableBrpJourney = true), awaitItem())
        }
    }
}
