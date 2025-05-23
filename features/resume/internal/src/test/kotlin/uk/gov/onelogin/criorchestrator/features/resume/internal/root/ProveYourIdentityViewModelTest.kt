package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeIsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeRefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.FakeResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val resourceProvider = FakeResourceProvider()
    private val isSessionResumable = FakeIsSessionResumable()
    private val refreshActiveSession =
        FakeRefreshActiveSession(
            fakeIsSessionResumable = isSessionResumable,
        )
    private var savedStateHandle = SavedStateHandle(emptyMap())
    private val viewModel by lazy {
        ProveYourIdentityViewModel.createTestInstance(
            isSessionResumable = isSessionResumable,
            refreshActiveSession = refreshActiveSession,
            analytics =
                ResumeAnalytics(
                    resourceProvider = resourceProvider,
                    analyticsLogger = analyticsLogger,
                ),
            savedStateHandle = savedStateHandle,
        )
    }

    private companion object {
        val SHOWN_STATE = ProveYourIdentityRootUiState(showCard = true)
        val HIDDEN_STATE = ProveYourIdentityRootUiState(showCard = false)
    }

    @Test
    fun `given no saved state and session is resumable, when started, card starts hidden then shows`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle = SavedStateHandle(emptyMap())
            viewModel.state.test {
                assertEquals(HIDDEN_STATE, awaitItem())
                viewModel.onScreenStart()
                assertEquals(SHOWN_STATE, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `given no saved state and session isn't resumable, when started, card stays hidden`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = false
            savedStateHandle =
                SavedStateHandle(emptyMap())
            viewModel.state.test {
                assertEquals(HIDDEN_STATE, awaitItem())
                viewModel.onScreenStart()
                expectNoEvents()
            }
        }

    @Test
    fun `given card was shown and session is resumable, when started, card stays shown`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SAVED_SHOW_CARD to true,
                    ),
                )
            viewModel.state.test {
                assertEquals(SHOWN_STATE, awaitItem())
                viewModel.onScreenStart()
                expectNoEvents()
            }
        }

    @Test
    fun `given card was shown and session isn't resumable, when started, card is shown then hidden`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = false
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SAVED_SHOW_CARD to true,
                    ),
                )
            viewModel.state.test {
                assertEquals(SHOWN_STATE, awaitItem())
                viewModel.onScreenStart()
                assertEquals(HIDDEN_STATE, awaitItem())
            }
        }

    @Test
    fun `given card was hidden and session is resumable, when started, card is hidden then shows`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SAVED_SHOW_CARD to false,
                    ),
                )
            viewModel.state.test {
                assertEquals(HIDDEN_STATE, awaitItem())
                viewModel.onScreenStart()
                assertEquals(SHOWN_STATE, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `given card was hidden and session isn't resumable, when started, card stays hidden`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = false
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SAVED_SHOW_CARD to false,
                    ),
                )
            viewModel.state.test {
                assertEquals(HIDDEN_STATE, awaitItem())
                viewModel.onScreenStart()
                expectNoEvents()
            }
        }

    @Test
    fun `given session changes, when started and restarted, card reflects the changes`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle = SavedStateHandle(emptyMap())
            viewModel.state.test {
                assertEquals(HIDDEN_STATE, awaitItem())
                viewModel.onScreenStart()
                assertEquals(SHOWN_STATE, awaitItem())

                refreshActiveSession.willHaveActiveSession = false
                viewModel.onScreenStart()
                assertEquals(HIDDEN_STATE, awaitItem())

                refreshActiveSession.willHaveActiveSession = true
                viewModel.onScreenStart()
                assertEquals(SHOWN_STATE, awaitItem())

                expectNoEvents()
            }
        }

    // Modal tests

    @Test
    fun `given no saved state and session is resumable, when started, modal is shown`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle = SavedStateHandle(emptyMap())
            viewModel.actions.test {
                viewModel.onScreenStart()
                assertEquals(ProveYourIdentityRootUiAction.AllowModalToShow, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `given card was hidden and session is resumable, when started, modal is shown`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SAVED_SHOW_CARD to false,
                    ),
                )
            viewModel.actions.test {
                viewModel.onScreenStart()
                assertEquals(ProveYourIdentityRootUiAction.AllowModalToShow, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `given card was shown and session is resumable, when started, modal is not re-shown`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SAVED_SHOW_CARD to true,
                    ),
                )
            viewModel.actions.test {
                viewModel.onScreenStart()
                expectNoEvents()
            }
        }

    @Test
    fun `given session becomes active two times, when started and restarted, modal is re-shown`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle = SavedStateHandle(emptyMap())
            viewModel.actions.test {
                viewModel.onScreenStart()
                assertEquals(ProveYourIdentityRootUiAction.AllowModalToShow, awaitItem())

                refreshActiveSession.willHaveActiveSession = false
                viewModel.onScreenStart()
                expectNoEvents()

                refreshActiveSession.willHaveActiveSession = true
                viewModel.onScreenStart()
                assertEquals(ProveYourIdentityRootUiAction.AllowModalToShow, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `given session remains active, when started and restarted, modal isn't re-shown`() =
        runTest {
            refreshActiveSession.willHaveActiveSession = true
            savedStateHandle = SavedStateHandle(emptyMap())
            viewModel.actions.test {
                viewModel.onScreenStart()
                assertEquals(ProveYourIdentityRootUiAction.AllowModalToShow, awaitItem())

                viewModel.onScreenStart()
                expectNoEvents()
            }
        }

    @Test
    fun `when start button is clicked, it sends analytics`() {
        viewModel.onStartClick()

        val expectedEvent: AnalyticsEvent =
            TrackEvent.Button(
                text = resourceProvider.defaultEnglishString,
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            )
        verify(analyticsLogger).logEventV3Dot1(expectedEvent)
    }

    @Test
    fun `when modal close button is clicked, it sends analytics`() {
        viewModel.onStartClick()

        val expectedEvent: AnalyticsEvent =
            TrackEvent.Button(
                text = resourceProvider.defaultEnglishString,
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            )
        verify(analyticsLogger).logEventV3Dot1(expectedEvent)
    }
}
