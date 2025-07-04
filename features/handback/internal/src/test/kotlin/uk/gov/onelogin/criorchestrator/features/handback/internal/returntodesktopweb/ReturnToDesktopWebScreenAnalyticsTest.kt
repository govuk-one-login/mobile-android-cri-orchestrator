package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.DebugRequestAppReview
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class ReturnToDesktopWebScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger

    private val analytics =
        HandbackAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel =
        ReturnToDesktopWebViewModel(
            analytics = analytics,
            requestAppReview =
                DebugRequestAppReview(
                    context = context,
                ),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ReturnToDesktopWebScreen(
                viewModel = viewModel,
            )
        }
    }

    @Test
    fun `when screen starts, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = HandbackScreenId.ReturnToDesktopWeb.rawId,
                    name = context.getString(ReturnToDesktopWebConstants.titleId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
