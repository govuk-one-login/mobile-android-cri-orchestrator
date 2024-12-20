package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun itLaunches() {
        launchActivity<MainActivity>().use {
            onView(withId(android.R.id.content))
                .check(matches(isDisplayed()))
        }
    }
}
