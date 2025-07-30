package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.testwrapper.logging.AnalyticsLoggerFactory
import uk.gov.onelogin.criorchestrator.testwrapper.logging.LoggerFactory

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        LoggerFactory.testLogger = mock<Logger>()
        AnalyticsLoggerFactory.testAnalyticsLogger = mock<AnalyticsLogger>()
    }

    @Test
    fun itLaunches() {
        launchActivity<MainActivity>().use {
            onView(withId(android.R.id.content))
                .check(matches(isDisplayed()))
        }
    }
}
