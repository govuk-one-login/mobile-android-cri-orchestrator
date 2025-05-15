package uk.gov.onelogin.criorchestrator.testwrapper.integration

import android.app.Application
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.sdk.publicapi.createTestInstance
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.MainContent
import org.robolectric.annotation.Config as RobolectricConfig

@RunWith(AndroidJUnit4::class)
@RobolectricConfig(application = Application::class)
class DeveloperSettingsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
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
    fun `developer settings survive configuration changes`() {
        stateRestorationTester.setContent {
            GdsTheme {
                MainContent(
                    criOrchestratorSdk = criOrchestratorSdk,
                    onSubUpdateRequest = {},
                )
            }
        }

        composeTestRule.setNfcAvailableDeveloperSetting(isNfcAvailable = true)

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.continueToSelectDocument()

        composeTestRule
            .onNodeWithText(DO_YOU_HAVE_A_PASSPORT)
            .performClick()

        composeTestRule.goBack(times = 3)

        composeTestRule.setNfcAvailableDeveloperSetting(isNfcAvailable = false)

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.continueToSelectDocument()

        composeTestRule
            .onAllNodesWithText(DO_YOU_HAVE_A_DRIVING_LICENCE)
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun `bypass id check async backend config changes are respected when restarting a journey`() {
        stateRestorationTester.setContent {
            GdsTheme {
                MainContent(
                    criOrchestratorSdk = criOrchestratorSdk,
                    onSubUpdateRequest = {},
                )
            }
        }

        composeTestRule.startJourney()

        composeTestRule
            .onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
            .assertIsDisplayed()

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule
            .onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
            .assertIsDisplayed()

        composeTestRule.goBack(times = 2)

        composeTestRule.toggleBypassIdCheckAsyncBackendConfig()

        composeTestRule.startJourney()

        composeTestRule
            .onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
            .assertIsNotDisplayed()
    }

    private fun ComposeTestRule.goBack(times: Int = 1) =
        repeat(times) {
            pressBack()
            composeTestRule.waitForIdle()
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

    private fun ComposeTestRule.toggleBypassIdCheckAsyncBackendConfig() {
        composeTestRule
            .onNodeWithText(DEVELOPER_SETTINGS, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNodeWithText(SdkConfigKey.BypassIdCheckAsyncBackend.name)
            .performClick()

        composeTestRule.goBack(times = 1)
    }

    private fun ComposeTestRule.startJourney() {
        composeTestRule
            .onNodeWithText(START)
            .performClick()
    }
}
