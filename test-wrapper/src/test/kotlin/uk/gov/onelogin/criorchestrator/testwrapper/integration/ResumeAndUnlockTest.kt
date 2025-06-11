package uk.gov.onelogin.criorchestrator.testwrapper.integration

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.libraries.composeutils.goBack
import uk.gov.onelogin.criorchestrator.sdk.publicapi.createTestInstance
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.MainContent
import org.robolectric.annotation.Config as RobolectricConfig

/**
 * TODO: This test is based on a copy-paste of another test.
 *   Refactor to share functionality.
 */
@RunWith(AndroidJUnit4::class)
@RobolectricConfig(application = Application::class)
class ResumeAndUnlockTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val stateRestorationTester = StateRestorationTester(composeTestRule)

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private companion object {
        private const val DEVELOPER_SETTINGS = "Developer settings"
        private const val START = "Start"
        private const val CONTINUE = "Continue"
        private const val CONTINUE_TO_PROVE_YOUR_IDENTITY = "Continue to prove your identity"
        private const val DO_YOU_HAVE_A_PASSPORT = "Do you have a passport with a biometric chip?"
        private const val DO_YOU_HAVE_A_DRIVING_LICENCE = "Do you have a UK photocard driving licence?"
    }

    val criOrchestratorSdk =
        CriOrchestratorSdk.createTestInstance(
            applicationContext = context,
            initialConfig =
                Config.createTestInstance(
                    isNfcAvailable = false,
                    bypassIdCheckAsyncBackend = true,
                ),
        )

    @Test
    fun `stop and start app and unlock`() {
        stateRestorationTester.setContent {
            GdsTheme {
                MainContent(
                    criOrchestratorSdk = criOrchestratorSdk,
                    onSubUpdateRequest = {},
                )
            }
        }
        composeTestRule
            .onNodeWithText("Unlock")
            .performClick()

        composeTestRule.setNfcAvailableDeveloperSetting(isNfcAvailable = true)

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule
            .onNodeWithText("Unlock")
            .performClick()

        composeTestRule.continueToSelectDocument()

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule
            .onNodeWithText("Unlock")
            .performClick()

        composeTestRule
            .onNodeWithText(DO_YOU_HAVE_A_PASSPORT)
            .assertIsDisplayed()
    }

    private fun ComposeTestRule.setNfcAvailableDeveloperSetting(isNfcAvailable: Boolean) {
        composeTestRule
            .onNodeWithText(DEVELOPER_SETTINGS, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(hasScrollToNodeAction())
            .performScrollToNode(hasText(NfcConfigKey.NfcAvailability.name))

        composeTestRule
            .onNodeWithText(NfcConfigKey.NfcAvailability.name)
            .performClick()

        val option =
            when (isNfcAvailable) {
                true -> NfcConfigKey.NfcAvailability.OPTION_AVAILABLE
                false -> NfcConfigKey.NfcAvailability.OPTION_NOT_AVAILABLE
            }
        composeTestRule
            .onNodeWithText(option, useUnmergedTree = true)
            .performClick()

        composeTestRule.goBack(times = 2)
    }

    private fun ComposeTestRule.continueToSelectDocument() {
        composeTestRule
            .onNodeWithText(START)
            .performClick()

        composeTestRule
            .onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(CONTINUE)
            .performClick()
    }
}
