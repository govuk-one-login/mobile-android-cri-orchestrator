package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubIsSessionAbortedOrUnavailable
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class AbortModalAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val analytics =
        HandbackAnalytics(
            resourceProvider = AndroidResourceProvider(context),
            analyticsLogger = analyticsLogger,
        )

    @Test
    fun `when close button is clicked on first screen, it tracks analytics`() {
        val isSessionAbortedOrUnavailable = StubIsSessionAbortedOrUnavailable(false)
        val abortModalViewModel =
            AbortModalViewModel(
                isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
                analytics = analytics,
            )

        composeTestRule.setContent {
            AbortModal(
                abortModalViewModel = abortModalViewModel,
                startDestination = AbortDestinations.ConfirmAbortDesktop,
                navGraphProviders = persistentSetOf(FakeAbortModalNavGraph.Provider()),
                onDismissRequest = {},
                onFinish = {},
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Close").performClick()

        composeTestRule.waitForIdle()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.handback_cancelbutton_analytics_text),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when close button is clicked on second screen, it tracks analytics`() {
        val isSessionAbortedOrUnavailable = StubIsSessionAbortedOrUnavailable(true)
        val abortModalViewModel =
            AbortModalViewModel(
                isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
                analytics = analytics,
            )

        composeTestRule.setContent {
            AbortModal(
                abortModalViewModel = abortModalViewModel,
                startDestination = AbortDestinations.AbortedReturnToDesktopWeb,
                navGraphProviders = persistentSetOf(FakeAbortModalNavGraph.Provider()),
                onDismissRequest = {},
                onFinish = {},
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Close").performClick()

        composeTestRule.waitForIdle()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.handback_cancelbutton_analytics_text,
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
