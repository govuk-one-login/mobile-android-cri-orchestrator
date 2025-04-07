package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

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
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class TypesOfPhotoIDScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val analytics =
        SelectDocAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel =
        TypesOfPhotoIDViewModel(
            analytics = analytics,
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            TypesOfPhotoIDScreen(
                viewModel = viewModel,
            )
        }
    }

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = SelectDocScreenId.TypesOfPhotoID.rawId,
                    name = context.getString(R.string.typesofphotoid_title),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }
}
