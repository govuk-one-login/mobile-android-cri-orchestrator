package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.FakeResourceProvider

class ProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()

    private val viewModel by lazy {
        ProveYourIdentityViewModel(
            analyticsLogger = analyticsLogger,
            resourceProvider = FakeResourceProvider(),
        )
    }

    private companion object {
        val INITIAL_STATE =
            ProveYourIdentityRootUiState(
                shouldDisplay = true,
            )
    }

    @Test
    fun `initial state`() =
        runTest {
            viewModel.state.test {
                assertEquals(INITIAL_STATE, awaitItem())
            }
        }

    @Test
    fun `when start button is clicked, it sends analytics`() {
        viewModel.start()

        val expectedEvent: AnalyticsEvent =
            TrackEvent.Button(
                text = "dummy string",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            )
        verify(analyticsLogger).logEventV3Dot1(expectedEvent)
    }
}
