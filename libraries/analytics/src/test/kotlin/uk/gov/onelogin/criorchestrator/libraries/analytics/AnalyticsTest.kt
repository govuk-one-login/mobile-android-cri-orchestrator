package uk.gov.onelogin.criorchestrator.libraries.analytics

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
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.ResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.analytics.testFixtures.R

class TestAnalytics(
    resourceProvider: ResourceProvider,
    analyticsLogger: AnalyticsLogger,
) : Analytics(
        resourceProvider,
        analyticsLogger,
        requiredParameters =
            RequiredParameters(
                taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                taxonomyLevel3 = TaxonomyLevel3.UNDEFINED,
            ),
    )

enum class TestScreenId(
    override val rawId: String,
) : ScreenId {
    ExampleScreen(rawId = "example-id"),
}

@RunWith(AndroidJUnit4::class)
class AnalyticsTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val context: Context = ApplicationProvider.getApplicationContext()

    companion object {
        @StringRes
        private val buttonText = R.string.test_button_text

        private val screenId = TestScreenId.ExampleScreen

        @StringRes
        private val screenTitle = R.string.test_screen_title
    }

    val analytics by lazy {
        TestAnalytics(
            resourceProvider = AndroidResourceProvider(context = context),
            analyticsLogger = analyticsLogger,
        )
    }

    private fun verifyTrackButtonEvent() {
        verify(analyticsLogger).logEventV3Dot1(
            TrackEvent.Button(
                text = "Continue",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.UNDEFINED,
                    ),
            ),
        )
    }

    private fun verifyTrackScreenEvent() {
        verify(analyticsLogger).logEventV3Dot1(
            ViewEvent.Screen(
                id = screenId.rawId,
                name = "Welcome to this app",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.UNDEFINED,
                    ),
            ),
        )
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
}
