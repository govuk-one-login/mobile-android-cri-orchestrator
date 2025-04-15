package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics

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
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider

@RunWith(AndroidJUnit4::class)
class SyncIdCheckAnalyticsTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val analytics by lazy {
        SyncIdCheckAnalytics(
            resourceProvider = AndroidResourceProvider(context = context),
            analyticsLogger = analyticsLogger,
        )
    }

    companion object {
        @StringRes
        val title = R.string.loading
    }

    @Test
    @Config(qualifiers = "en")
    fun `given english locale, it tracks analytics`() {
        analytics.trackScreen(
            id = SyncIdCheckScreenId.SyncIdCheckScreen,
            title = title,
        )

        verifyTrackingEvent()
    }

    @Test
    @Config(qualifiers = "en")
    fun `given welsh locale, it tracks analytics`() {
        analytics.trackScreen(
            id = SyncIdCheckScreenId.SyncIdCheckScreen,
            title = title,
        )

        verifyTrackingEvent()
    }

    private fun verifyTrackingEvent() {
        verify(analyticsLogger).logEventV3Dot1(
            ViewEvent.Screen(
                id = SyncIdCheckScreenId.SyncIdCheckScreen.rawId,
                name = "Loading",
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.DOCUMENT_SELECTION,
                    ),
            ),
        )
    }
}
