package uk.gov.onelogin.criorchestrator.testwrapper.integration

import android.app.Application
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
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

    val criOrchestratorSdk =
        CriOrchestratorSdk.createTestInstance(
            applicationContext = context,
            initialConfig =
                Config.createTestInstance(
                    isNfcAvailable = false,
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
            .onNodeWithText("Do you have a passport with a biometric chip?")
            .performClick()

        composeTestRule.goBack(times = 3)

        composeTestRule.setNfcAvailableDeveloperSetting(isNfcAvailable = false)

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.continueToSelectDocument()

        composeTestRule
            .onAllNodesWithText("Do you have a UK photocard driving licence?")
            .onFirst()
            .assertIsDisplayed()
    }

    private fun ComposeTestRule.goBack(times: Int = 1) =
        repeat(times) {
            pressBack()
            composeTestRule.waitForIdle()
        }

    private fun ComposeTestRule.setNfcAvailableDeveloperSetting(isNfcAvailable: Boolean) {
        composeTestRule
            .onNodeWithText("Developer settings", useUnmergedTree = true)
            .performClick()

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
            .onNodeWithText("Start")
            .performClick()

        composeTestRule
            .onNodeWithText("Continue to prove your identity")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Continue")
            .performClick()
    }
}
