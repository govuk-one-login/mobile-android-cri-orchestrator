package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class ReturnToMobileWebScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

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
        ReturnToMobileWebViewModel(
            analytics = analytics,
            sessionStore = FakeSessionStore(),
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ReturnToMobileWebScreen(
                viewModel = viewModel,
                webNavigator = FakeWebNavigator(),
            )
        }
    }

    @Test
    fun `when screen starts, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = HandbackScreenId.ReturnToMobileWeb.rawId,
                    name = context.getString(ReturnToMobileWebConstants.titleId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when continue to gov uk website is clicked, it tracks analytics`() {
        composeTestRule
            .onNode(
                hasTextStartingWith(context.getString(ReturnToMobileWebConstants.buttonId)),
            ).performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(ReturnToMobileWebConstants.buttonId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
