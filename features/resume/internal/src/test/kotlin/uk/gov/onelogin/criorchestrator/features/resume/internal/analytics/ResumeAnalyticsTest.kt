package uk.gov.onelogin.criorchestrator.features.resume.internal.analytics

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
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.AndroidResourceProvider

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

        val screenId = ScreenId.ContinueToProveYourIdentity

        @StringRes
        val screenTitle = R.string.start_id_check_title
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

    @Test
    @Config(qualifiers = "en")
    fun `given english locale, track screen tracks correctly`() {
        analytics.trackScreen(
            id = screenId,
            title = screenTitle,
        )
        verifyTrackScreenEvent()
    }

    @Test
    @Config(qualifiers = "cy")
    fun `given welsh locale, track screen tracks correctly`() {
        analytics.trackScreen(
            id = screenId,
            title = screenTitle,
        )
        verifyTrackScreenEvent()
    }

    private fun verifyTrackButtonEvent() {
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

    private fun verifyTrackScreenEvent() {
        verify(analyticsLogger).logEventV3Dot1(
            ViewEvent.Screen(
                id = screenId.rawId,
                name = "Prove your identity",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            ),
        )
    }
}
