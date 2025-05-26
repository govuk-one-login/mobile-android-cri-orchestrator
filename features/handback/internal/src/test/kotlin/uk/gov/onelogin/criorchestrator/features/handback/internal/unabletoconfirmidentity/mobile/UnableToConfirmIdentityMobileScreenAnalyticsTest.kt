package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile

import android.content.Context
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
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackScreenId
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class UnableToConfirmIdentityMobileScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val continueButton = hasTextStartingWith(context.getString(UnableToConfirmIdentityMobileConstants.buttonId))
    private val sessionStore = FakeSessionStore()
    private val abortSession = StubAbortSession()
    private val logger = SystemLogger()
    private val analytics =
        HandbackAnalytics(
            resourceProvider = AndroidResourceProvider(context),
            analyticsLogger = analyticsLogger,
        )

    private val viewmodel by lazy {
        UnableToConfirmIdentityMobileViewModel(
            sessionStore = sessionStore,
            analytics = analytics,
            abortSession = abortSession,
            logger = logger,
        )
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            UnableToConfirmIdentityMobileScreen(
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
                    id = HandbackScreenId.UnableToConfirmIdentityMobile.rawId,
                    name = context.getString(UnableToConfirmIdentityMobileConstants.titleId),
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
                    text = context.getString(UnableToConfirmIdentityMobileConstants.buttonId),
                    params = HandbackAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
