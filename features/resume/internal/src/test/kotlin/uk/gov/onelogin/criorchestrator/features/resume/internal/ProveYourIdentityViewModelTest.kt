package uk.gov.onelogin.criorchestrator.features.resume.internal

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger

class FakeResourceProvider : ResourceProvider {
    override fun getEnglishString(resId: Int): String = "dummy string"
}

class ProveYourIdentityViewModelTest {
    private val analyticsLogger = FakeAnalyticsLogger()

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
        assertTrue(expectedEvent.asLegacyEvent() in analyticsLogger)
    }
}
