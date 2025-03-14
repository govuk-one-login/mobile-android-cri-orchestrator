package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.robolectric.annotation.Config
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.AndroidResourceProvider

@RunWith(AndroidJUnit4::class)
class SelectDocumentAnalyticsTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val analytics by lazy {
        SelectDocumentAnalytics(
            resourceProvider = AndroidResourceProvider(context = context),
            analyticsLogger = analyticsLogger,
        )
    }

    companion object {
        @StringRes
        val buttonText = R.string.selectdocument_passport_readmore_button
    }

    @Test
    @Config(qualifiers = "en")
    fun `given english locale, track button tracks correctly`() {
        analytics.trackButtonEvent(buttonText = buttonText)
        verifyTrackButtonEvent()
    }

    @Test
    @Config(qualifiers = "cy")
    fun `given welsh locale, track button tracks correctly`() {
        analytics.trackButtonEvent(buttonText = buttonText)
        verifyTrackButtonEvent()
    }

    private fun verifyTrackButtonEvent() {
        verify(analyticsLogger).logEventV3Dot1(
            TrackEvent.Button(
                text = "Read more about the types of photo ID you can use",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.DOCUMENT_SELECTION,
                    ),
            ),
        )
    }
}
