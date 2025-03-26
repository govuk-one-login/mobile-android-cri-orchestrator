package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule

@RunWith(AndroidJUnit4::class)
class SelectPassportScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val readMoreButton = hasText(context.getString(R.string.selectdocument_passport_readmore_button))
    private val continueButton = hasText(context.getString(R.string.selectdocument_passport_continuebutton))
    private val yesOption = hasText(context.getString(R.string.selectdocument_passport_selection_yes))
    private val image = hasContentDescription(context.getString(R.string.selectdocument_passport_imagedescription))

    private val analytics =
        SelectDocumentAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel =
        SelectPassportViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedScreenName =
            context.getString(R.string.selectdocument_passport_title)
        val expectedScreenId = SelectDocumentScreenId.SelectPassport.rawId
        composeTestRule.setSelectPassportScreenContent()
        val matchingEvents =
            analyticsLogger.loggedEvents.filter {
                it.parameters["screen_id"] == expectedScreenId &&
                    it.parameters["screen_name"] == expectedScreenName
            }
        assertEquals(1, matchingEvents.size)
    }

    @Test
    fun `given selection is made, when continue button is clicked, it tracks analytics`() {
        composeTestRule.setSelectPassportScreenContent()

        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        val matchingEvents =
            analyticsLogger.loggedEvents.filter {
                it.parameters["text"] == context.getString(R.string.selectdocument_passport_continuebutton)
            }
        assertEquals(1, matchingEvents.size)
    }

    @Test
    fun `when read more is clicked, it tracks analytics`() {
        composeTestRule.setSelectPassportScreenContent()

        swipeToAdditionalContent()

        composeTestRule
            .onNode(readMoreButton)
            .performClick()

        val matchingEvents =
            analyticsLogger.loggedEvents.filter {
                it.parameters["text"] == context.getString(R.string.selectdocument_passport_readmore_button)
            }
        assertEquals(1, matchingEvents.size)
    }

    private fun ComposeContentTestRule.setSelectPassportScreenContent() {
        setContent {
            SelectPassportScreen(
                viewModel = viewModel,
                navController = rememberNavController(),
            )
        }
    }

    private fun swipeToAdditionalContent() {
        composeTestRule
            .onNode(
                image,
            ).onParent()
            .performTouchInput {
                swipeUp()
            }
    }
}
