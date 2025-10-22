package uk.gov.onelogin.criorchestrator.testwrapper

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.publicapi.createTestInstance
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONFIRM_CONTINUE_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE_TO_GOVUK
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.CONTINUE_TO_PROVE_YOUR_IDENTITY
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.DO_YOU_HAVE_A_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.LOADING
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.RETURN_TO_GOVUK_MOBILE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.SELECT_BIOMETRIC_TOKEN_RESULT
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.START
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.SUCCESS
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.YES

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class IdCheckIntegrationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val stateRestorationTester = StateRestorationTester(composeTestRule)

    @get:Rule
    val permissionCamera: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.CAMERA,
        )

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val testScope = TestScope()

    val criOrchestratorSdk =
        CriOrchestratorSdk.createTestInstance(
            applicationContext = context,
            initialConfig =
                Config.createTestInstance(
                    isNfcAvailable = false,
                    bypassIdCheckAsyncBackend = true,
                    enableManualIdCheckSdkLauncher = false,
                ),
            testDispatcher = UnconfinedTestDispatcher(testScope.testScheduler),
            analyticsLogger = FakeAnalyticsLogger(),
            logger = SystemLogger(),
        )

    @Test
    fun testDrivingLicenceMamHappyPath() =
        testScope.runTest {
            stateRestorationTester.setContent {
                GdsTheme {
                    MainContent(
                        criOrchestratorSdk = criOrchestratorSdk,
                        onSubUpdateRequest = {},
                    )
                }
            }

            composeTestRule
                .onNodeWithText(START)
                .performClick()

            composeTestRule
                .onNodeWithText(CONTINUE_TO_PROVE_YOUR_IDENTITY)
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithText(CONTINUE)
                .performClick()

            composeTestRule
                .onNode(isHeading() and hasText(DO_YOU_HAVE_A_DRIVING_LICENCE))
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(YES, useUnmergedTree = true)
                .performScrollTo()
                .performClick()
            composeTestRule
                .onNodeWithText(CONTINUE)
                .performClick()

            composeTestRule
                .onNode(isHeading() and hasText(CONFIRM_CONTINUE_DRIVING_LICENCE))
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(CONFIRM)
                .performClick()

            composeTestRule
                .onNodeWithText(LOADING)
                .assertIsDisplayed()

            testScheduler.advanceTimeBy(3000)

            composeTestRule
                .onNode(hasText(SELECT_BIOMETRIC_TOKEN_RESULT))
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(SUCCESS, useUnmergedTree = true)
                .performClick()
            onView(withText("Take a photo of the front of your driving licence")).check(matches(isDisplayed()))
            onView(withText("Start")).perform(click())
            onView(withText("This is the photo you have taken")).check(matches(isDisplayed()))
            onView(withText("Submit photo")).perform(click())
            onView(withText("Take a photo of the back of your driving licence")).check(matches(isDisplayed()))
            onView(withText("Start")).perform(click())
            onView(withText("This is the photo you have taken")).check(matches(isDisplayed()))
            onView(withText("Submit photo")).perform(click())
            onView(withText("Photo submitted")).check(matches(isDisplayed()))
            onView(withText("Scan your face with your selfie camera")).check(matches(isDisplayed()))
            onView(withText("Start")).perform(click())
            composeTestRule
                .onNode(hasText(RETURN_TO_GOVUK_MOBILE))
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(CONTINUE_TO_GOVUK, useUnmergedTree = true, substring = true)
                .performClick()
        }
}
