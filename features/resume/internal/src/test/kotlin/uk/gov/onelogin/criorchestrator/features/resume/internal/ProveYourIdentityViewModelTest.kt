package uk.gov.onelogin.criorchestrator.features.resume.internal

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

class FakeResourceProvider : ResourceProvider {
    override fun getEnglishString(resId: Int): String = "dummy string"
}

class ProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()

    private val viewModel by lazy {
        ProveYourIdentityViewModel(
            analyticsLogger = analyticsLogger,
            resourceProvider = FakeResourceProvider(),
        )
    }

    @Test
    fun sendsAnalytics() {
        viewModel.start()

        val expectedEvent: AnalyticsEvent =
            TrackEvent.Button(
                text = "dummy string",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.PASSPORT_CRI,
                    ),
            )
        verify(analyticsLogger).logEventV3Dot1(expectedEvent)
    }
}
