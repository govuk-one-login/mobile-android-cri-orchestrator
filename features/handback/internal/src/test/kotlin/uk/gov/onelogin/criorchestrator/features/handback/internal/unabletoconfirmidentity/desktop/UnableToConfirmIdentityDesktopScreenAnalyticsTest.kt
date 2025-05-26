package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.desktop

import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class UnableToConfirmIdentityDesktopScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val continueButton = hasText(context.getString(UnableToConfirmIdentityDesktopConstants.buttonId))
    private val abortSession = StubAbortSession()
    private val analytics =
        HandbackAnalytics(
            resourceProvider = AndroidResourceProvider(context),
            analyticsLogger = analyticsLogger,
        )

    private val viewmodel by lazy {
        UnableToConfirmIdentityDesktopViewModel(
            analytics = analytics,
            abortSession = abortSession,
        )
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            UnableToConfirmIdentityDesktopScreen(
                viewModel = viewmodel,
                navController = mock(),
            )
        }
    }

    @Test
    fun `when screen starts, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = HandbackScreenId.UnableToConfirmIdentityDesktop.rawId,
                    name = context.getString(UnableToConfirmIdentityDesktopConstants.titleId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when continue button is clicked, it tracks analytics`() {
        composeTestRule
            .onNode(continueButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(UnableToConfirmIdentityDesktopConstants.buttonId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
