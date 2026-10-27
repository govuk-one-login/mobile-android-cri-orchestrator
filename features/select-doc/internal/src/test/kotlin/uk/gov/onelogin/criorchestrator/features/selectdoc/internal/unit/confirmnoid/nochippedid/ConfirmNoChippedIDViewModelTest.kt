package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.unit.confirmnoid.nochippedid

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
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDAction
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDState
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ConfirmNoChippedIDViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private val fakeSessionStoreDisableBrp = FakeSessionStore(Session.createTestInstance(disableBrpJourney = true))
    private val fakeSessionStoreDefault = FakeSessionStore(Session.createTestInstance())

    @Test
    fun `when screen starts, it sends analytics`() {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType = StubGetJourneyType(),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.ConfirmNoChippedID,
                title = R.string.confirm_nochippedid_title,
            )
    }

    @Test
    fun `when confirm is pressed, send analytics`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType = StubGetJourneyType(),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.action.test {
            viewModel.onConfirmClick()
            verify(analyticsLogger)
                .trackButtonEvent(
                    buttonText = R.string.confirm_nochippedid_confirmbutton,
                )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when confirm is pressed and journey is MAM, it navigates to Confirm Abort Mobile screen`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
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
                    buttonText = R.string.confirm_nochippedid_confirmbutton,
                )
            assertEquals(ConfirmNoChippedIDAction.NavigateToConfirmAbortMobile, awaitItem())
        }
    }

    @Test
    fun `when confirm is pressed and journey is DAD, it navigates to Confirm Abort Desktop screen`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
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
                    buttonText = R.string.confirm_nochippedid_confirmbutton,
                )
            assertEquals(ConfirmNoChippedIDAction.NavigateToConfirmAbortDesktop, awaitItem())
        }
    }

    @Test
    fun `when stored session does not contain disableBrpJourney flag it is not set in state`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.DesktopAppDesktop,
                    ),
                sessionStore = fakeSessionStoreDefault,
            )

        viewModel.state.test {
            assertEquals(ConfirmNoChippedIDState(disableBrpJourney = false), awaitItem())
        }
    }

    @Test
    fun `when stored session does contains disableBrpJourney flag it is set in state`() = runTest {
        val viewModel =
            ConfirmNoChippedIDViewModel(
                analytics = analyticsLogger,
                getJourneyType =
                    StubGetJourneyType(
                        journeyType = JourneyType.DesktopAppDesktop,
                    ),
                sessionStore = fakeSessionStoreDisableBrp,
            )

        viewModel.state.test {
            assertEquals(ConfirmNoChippedIDState(disableBrpJourney = true), awaitItem())
        }
    }
}
