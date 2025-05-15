package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class ConfirmAbortMobileScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val continueButton = hasTextStartingWith(context.getString(ConfirmAbortMobileConstants.buttonId))

    private val analytics =
        HandbackAnalytics(
            resourceProvider = AndroidResourceProvider(context),
            analyticsLogger = analyticsLogger,
        )

    private val viewmodel =
        ConfirmAbortMobileViewModel(
            analytics = analytics,
            sessionStore = FakeSessionStore(),
            abortSession = StubAbortSession(),
        )

    private val navController = mock<NavController>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ConfirmAbortMobileScreen(
                viewModel = viewmodel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when screen starts, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = HandbackScreenId.ConfirmAbortMobile.rawId,
                    name = context.getString(ConfirmAbortMobileConstants.titleId),
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
                    text = context.getString(ConfirmAbortMobileConstants.buttonId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
