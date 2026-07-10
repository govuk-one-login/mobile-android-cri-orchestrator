package uk.gov.onelogin.criorchestrator.features.handback.internal.novalidsession

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoValidSessionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var onScreenStartCalled = false
    private val title = hasText("We were not able to confirm your identity")
    private val para1 = hasText("This could be because you started proving your identity more than an hour ago.")
    private val para2 = hasText("You need to start again.")
    private val para3 =
        hasText(
            "Go back to the GOV.UK website. Find the service you need to use and try " +
                "to prove your identity again.",
        )

    @Before
    fun setup() {
        composeTestRule.setContent {
            NoValidSessionScreen {
                onScreenStartCalled = true
            }
        }
    }

    @Test
    fun verifyOnScreenStartCalled() {
        assertTrue("onScreenStart callback not called!", onScreenStartCalled)
    }

    @Test
    fun verifyStrings() {
        composeTestRule.apply {
            onNode(title).assertExists()
            onNode(para1).assertExists()
            onNode(para2).assertExists()
            onNode(para3).assertExists()
        }
    }
}
