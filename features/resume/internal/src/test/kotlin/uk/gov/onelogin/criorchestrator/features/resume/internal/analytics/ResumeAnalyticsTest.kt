package uk.gov.onelogin.criorchestrator.features.resume.internal.analytics

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider

@RunWith(AndroidJUnit4::class)
class ResumeAnalyticsTest {
    val analyticsLogger = mock<AnalyticsLogger>()
    val context: Context = ApplicationProvider.getApplicationContext()

    val analytics by lazy {
        ResumeAnalytics(
            resourceProvider = AndroidResourceProvider(context = context),
            analyticsLogger = analyticsLogger,
        )
    }

    companion object {
        @StringRes
        val buttonText = R.string.start_id_check_primary_button
    }

    @Test
    fun verifyRequiredParameters() {
        analytics.trackButtonEvent(buttonText = buttonText)

        verify(analyticsLogger).logEventV3Dot1(
            TrackEvent.Button(
                text = "Continue proving your identity",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            ),
        )
    }
}
