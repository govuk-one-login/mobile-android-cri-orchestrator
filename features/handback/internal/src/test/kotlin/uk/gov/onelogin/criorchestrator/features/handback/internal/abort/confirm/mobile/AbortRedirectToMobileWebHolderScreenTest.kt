package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI

@RunWith(AndroidJUnit4::class)
class AbortRedirectToMobileWebHolderScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val webNavigator = FakeWebNavigator()
    private var hasFinished = false

    @Before
    fun setup() {
        hasFinished = false
        composeTestRule.setContent {
            AbortRedirectToMobileWebHolderScreen(
                redirectUri = REDIRECT_URI,
                webNavigator = webNavigator,
                onFinish = ::onFinish,
            )
        }
    }

    @Test
    fun `when navigated to, it immediately navigates to mobile web`() {
        assertEquals(REDIRECT_URI, webNavigator.openUrl)
    }

    @Test
    fun `when navigated to, it immediately runs onFinish`() {
        assertTrue(hasFinished)
    }

    private fun onFinish() {
        hasFinished = true
    }
}
