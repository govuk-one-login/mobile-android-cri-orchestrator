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
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubSessionReader
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.FakeResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val resourceProvider = FakeResourceProvider()
    private var savedStateHandle = SavedStateHandle(emptyMap())
    private var sessionReader: SessionReader = StubSessionReader()
    private val viewModel by lazy {
        ProveYourIdentityViewModel.createTestInstance(
            sessionReader = sessionReader,
            analytics =
                ResumeAnalytics(
                    resourceProvider = resourceProvider,
                    analyticsLogger = analyticsLogger,
                ),
            savedStateHandle = savedStateHandle,
        )
    }

    private companion object {
        val INITIAL_STATE =
            ProveYourIdentityRootUiState(
                shouldDisplay = true,
            )
    }

    @Test
    fun `given no saved state and active session, initial state is created`() =
        runTest {
            sessionReader = StubSessionReader(isActiveSession = true)
            savedStateHandle =
                SavedStateHandle(emptyMap())
            val expected = INITIAL_STATE.copy(shouldDisplay = true)
            viewModel.state.test {
                assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given no saved state and no session, initial state is created`() =
        runTest {
            sessionReader = StubSessionReader(isActiveSession = false)
            savedStateHandle =
                SavedStateHandle(emptyMap())
            val expected = INITIAL_STATE.copy(shouldDisplay = false)
            viewModel.state.test {
                assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given saved state and no session, initial state is restored`() =
        runTest {
            sessionReader = StubSessionReader(isActiveSession = false)
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SHOULD_DISPLAY_KEY to true,
                    ),
                )
            val expected = INITIAL_STATE.copy(shouldDisplay = true)
            viewModel.state.test {
                assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given saved state and active session, initial state is restored`() =
        runTest {
            sessionReader = StubSessionReader(isActiveSession = true)
            savedStateHandle =
                SavedStateHandle(
                    mapOf(
                        ProveYourIdentityViewModel.SHOULD_DISPLAY_KEY to true,
                    ),
                )
            val expected = INITIAL_STATE.copy(shouldDisplay = true)
            viewModel.state.test {
                assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `when start button is clicked, it sends analytics`() {
        viewModel.start()

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
        viewModel.start()

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
