package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityDestinations

@RunWith(AndroidJUnit4::class)
class UnableToConfirmIdentityModalTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `modal launches`() {
        composeTestRule.setContent {
            UnableToConfirmIdentityModal(
                startDestination = UnableToConfirmIdentityDestinations.UnableToConfirmIdentityMobile,
                navGraphProviders = persistentSetOf(FakeUnableToConfirmIdentityModalNavGraph.Provider()),
                onFinish = { },
            )
        }
        composeTestRule.onNodeWithText("Test Text").assertIsDisplayed()
    }
}