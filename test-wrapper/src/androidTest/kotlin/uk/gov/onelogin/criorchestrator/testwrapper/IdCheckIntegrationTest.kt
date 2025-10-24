package uk.gov.onelogin.criorchestrator.testwrapper

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.PHOTO_SUBMITTED
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.SCAN_FACE_WITH_SELFIE_CAMERA
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.START
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.SUBMIT_PHOTO
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.TAKE_PHOTO_OF_BACK_OF_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.TAKE_PHOTO_OF_FRONT_OF_DRIVING_LICENCE
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.THIS_IS_THE_PHOTO_YOU_HAVE_TAKEN
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.confirmContinueWithDrivingLicence
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.confirmDoYouHaveADrivingLicence
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.continueToGovUkWebsite
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.continueToSelectDocument
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.seeLoading
import uk.gov.onelogin.criorchestrator.testwrapper.testfixtures.ruleext.selectBiometricTokenResult
import kotlin.time.Duration.Companion.seconds

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

            composeTestRule.continueToSelectDocument()
            composeTestRule.confirmDoYouHaveADrivingLicence(hasDrivingLicence = true)
            composeTestRule.confirmContinueWithDrivingLicence()
            composeTestRule.seeLoading(testScope, 3.seconds)
            composeTestRule.selectBiometricTokenResult()

            // ID Check SDK launches

            onView(withText(TAKE_PHOTO_OF_FRONT_OF_DRIVING_LICENCE)).check(matches(isDisplayed()))
            onView(withText(START)).perform(click())
            onView(withText(THIS_IS_THE_PHOTO_YOU_HAVE_TAKEN)).check(matches(isDisplayed()))
            onView(withText(SUBMIT_PHOTO)).perform(click())
            onView(withText(TAKE_PHOTO_OF_BACK_OF_DRIVING_LICENCE)).check(matches(isDisplayed()))
            onView(withText(START)).perform(click())
            onView(withText(THIS_IS_THE_PHOTO_YOU_HAVE_TAKEN)).check(matches(isDisplayed()))
            onView(withText(SUBMIT_PHOTO)).perform(click())
            onView(withText(PHOTO_SUBMITTED)).check(matches(isDisplayed()))
            onView(withText(SCAN_FACE_WITH_SELFIE_CAMERA)).check(matches(isDisplayed()))
            onView(withText(START)).perform(click())

            // ID Check SDK finishes

            composeTestRule.continueToGovUkWebsite()
        }
}
