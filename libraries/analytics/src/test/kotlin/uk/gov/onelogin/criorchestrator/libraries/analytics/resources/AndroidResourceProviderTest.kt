package uk.gov.onelogin.criorchestrator.libraries.analytics.resources

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.libraries.analytics.testFixtures.R

@RunWith(AndroidJUnit4::class)
class AndroidResourceProviderTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val testAndroidResourceProvider = AndroidResourceProvider(context)

    @Test
    fun `resource provider provides desired string`() {
        // Even though it looks like it can't resolve the string, when the test runs it resolves.
        val testString = testAndroidResourceProvider.getEnglishString(R.string.test_string)
        val expectedString = "test string"

        assertEquals(expectedString, testString)
    }
}
