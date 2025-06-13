package uk.gov.onelogin.criorchestrator.testwrapper.integration

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.libraries.composeutils.goBack
import uk.gov.onelogin.criorchestrator.sdk.publicapi.createTestInstance
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.MainContent
import uk.gov.onelogin.criorchestrator.testwrapper.MainContentTestAction
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ANOTHER_SCREEN
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.DO_YOU_HAVE_A_PASSPORT
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
                    isNfcAvailable = true,
                    bypassIdCheckAsyncBackend = true,
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

            testActions.emit(MainContentTestAction.NavigateToAnotherScreen)

            composeTestRule
                .onNodeWithText(ANOTHER_SCREEN)
                .assertIsDisplayed()

            composeTestRule.goBack()

            composeTestRule
                .onNodeWithText(DO_YOU_HAVE_A_PASSPORT)
                .assertIsDisplayed()
        }
}
