package uk.gov.onelogin.criorchestrator.testwrapper.integration

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.composeutils.goBack
import uk.gov.onelogin.criorchestrator.sdk.publicapi.createTestInstance
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.MainContent
import uk.gov.onelogin.criorchestrator.testwrapper.MainContentTestAction
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ANOTHER_SCREEN
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.DO_YOU_HAVE_A_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.NO
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.RETURN_TO_GOVUK_ON_COMPUTER
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.continueToAbortedDesktop
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.continueToSelectDocument
import org.robolectric.annotation.Config as RobolectricConfig

@RunWith(AndroidJUnit4::class)
@RobolectricConfig(application = Application::class)
class RestoreProveYourIdentityNavigationStateTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val stateRestorationTester = StateRestorationTester(composeTestRule)

    private val context = ApplicationProvider.getApplicationContext<Context>()

    val criOrchestratorSdk =
        CriOrchestratorSdk.createTestInstance(
            applicationContext = context,
            initialConfig =
                Config.createTestInstance(
                    isNfcAvailable = false,
                    bypassIdCheckAsyncBackend = true,
                    bypassJourneyType = SdkConfigKey.BypassJourneyType.OPTION_DESKTOP_APP_DESKTOP,
                ),
        )

    @Test
    fun `restore 'prove your identity' navigation state within another nav graph`() =
        runTest {
            val testActions = MutableSharedFlow<MainContentTestAction>()
            stateRestorationTester.setContent {
                GdsTheme {
                    MainContent(
                        criOrchestratorSdk = criOrchestratorSdk,
                        onSubUpdateRequest = {},
                        testActions = testActions,
                    )
                }
            }

            composeTestRule.continueToSelectDocument()

            composeTestRule
                .onNodeWithText(NO, useUnmergedTree = true)
                .performScrollTo()
                .performClick()

            testActions.emit(MainContentTestAction.NavigateToAnotherScreen)

            composeTestRule
                .onNodeWithText(ANOTHER_SCREEN)
                .assertIsDisplayed()

            composeTestRule.goBack()

            composeTestRule
                .onNode(isHeading() and hasText(DO_YOU_HAVE_A_DRIVING_LICENCE))
                // It's not displayed because we scrolled down earlier
                .assertExists()

            composeTestRule
                .onNode(hasAnySibling(hasText(NO)) and isSelectable(), useUnmergedTree = true)
                .assertIsSelected()
        }

    @Test
    fun `restore 'abort' navigation state within another nav graph`() =
        runTest {
            val testActions = MutableSharedFlow<MainContentTestAction>()
            stateRestorationTester.setContent {
                GdsTheme {
                    MainContent(
                        criOrchestratorSdk = criOrchestratorSdk,
                        onSubUpdateRequest = {},
                        testActions = testActions,
                    )
                }
            }

            composeTestRule.continueToAbortedDesktop()

            testActions.emit(MainContentTestAction.NavigateToAnotherScreen)

            composeTestRule
                .onNodeWithText(ANOTHER_SCREEN)
                .assertIsDisplayed()

            composeTestRule.goBack()

            composeTestRule
                .onNodeWithText(RETURN_TO_GOVUK_ON_COMPUTER)
                .assertIsDisplayed()
        }
}
