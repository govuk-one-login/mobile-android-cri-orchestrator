package uk.gov.onelogin.criorchestrator.features.handback.internal.navigation

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.serialization.Serializable
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.handback.internal.HandbackNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop.AbortedReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.analytics.HandbackAnalytics
import uk.gov.onelogin.criorchestrator.features.handback.internal.appreview.FakeRequestAppReview
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop.FaceScanLimitReachedDesktopViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile.FaceScanLimitReachedMobileViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModalNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModalViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubAbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubIsSessionAbortedOrUnavailable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.libraries.composeutils.filterInDialogElseAll
import uk.gov.onelogin.criorchestrator.libraries.composeutils.goBack
import uk.gov.onelogin.criorchestrator.libraries.navigation.CompositeNavHost
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class HandbackNavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val analytics: HandbackAnalytics = mock()
    private val getJourneyType = StubGetJourneyType()
    private val isSessionAbortedOrUnavailable = StubIsSessionAbortedOrUnavailable(false)
    private val sessionStore = FakeSessionStore()
    private val webNavigator = FakeWebNavigator()
    private val abortSession = StubAbortSession()
    private val requestAppReview = FakeRequestAppReview()
    private val logger = SystemLogger()
    private val navGraphProvider = createNavGraphProvider()
    private val onFinish = mock<() -> Unit>()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(onFinish)
    }

    @Test
    fun `return to desktop`() {
        givenDesktopJourney()
        composeTestRule.setNavGraphContent(
            startNavigatesTo = HandbackDestinations.ReturnToDesktopWeb,
        )

        composeTestRule.clickStart()
        composeTestRule.assertReturnToDesktopWebIsDisplayed()

        // Back navigation is disabled
        composeTestRule.goBack()
        composeTestRule.assertReturnToDesktopWebIsDisplayed()
    }

    @Test
    fun `return to mobile web`() {
        val redirectUri = "http://mam-redirect-uri?state=mock-state"
        givenMobileJourney(
            redirectUri = redirectUri,
        )
        composeTestRule.setNavGraphContent(
            startNavigatesTo = HandbackDestinations.ReturnToMobileWeb(REDIRECT_URI),
        )

        composeTestRule.clickStart()
        composeTestRule.assertReturnToMobileWebIsDisplayed()

        // Back navigation is disabled
        composeTestRule.goBack()
        composeTestRule.assertReturnToMobileWebIsDisplayed()

        composeTestRule.clickContinueToGovUkWebsite()
        composeTestRule.waitForIdle()

        assertEquals(redirectUri, webNavigator.openUrl)
    }

    @Test
    fun `confirm abort - desktop`() {
        givenDesktopJourney()
        composeTestRule.setNavGraphContent(
            startNavigatesTo = AbortDestinations.ConfirmAbortDesktop,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmAbortIsDisplayed()

        composeTestRule.clickConfirmAbortToDesktopWebButton()
        composeTestRule.assertAbortedReturnToDesktopWebIsDisplayed()
    }

    @Test
    fun `confirm abort - mobile`() {
        val redirectUri = "https://redirect-uri"
        givenMobileJourney(
            redirectUri = redirectUri,
        )
        composeTestRule.setNavGraphContent(
            startNavigatesTo = AbortDestinations.ConfirmAbortMobile,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmAbortIsDisplayed()

        composeTestRule.clickContinueToGovUkWebsite()
        composeTestRule.waitForIdle()

        assertEquals(redirectUri, webNavigator.openUrl)
        verify(onFinish).invoke()
    }

    // This scenario may happen if the session was aborted in the ID Check SDK
    @Test
    fun `already aborted - desktop`() {
        givenAbortedSession()
        composeTestRule.setNavGraphContent(
            startNavigatesTo = AbortDestinations.AbortedReturnToDesktopWeb,
        )

        composeTestRule.clickStart()
        composeTestRule.assertAbortedReturnToDesktopWebIsDisplayed()

        // Back navigation is disabled
        composeTestRule.goBack()
        composeTestRule.assertAbortedReturnToDesktopWebIsDisplayed()
    }

    // This scenario may happen if the session was aborted in the ID Check SDK
    @Test
    fun `already aborted - mobile`() {
        givenAbortedSession()
        val redirectUri = "https://redirect-uri"
        composeTestRule.setNavGraphContent(
            startNavigatesTo =
                AbortDestinations.AbortedRedirectToMobileWebHolder(
                    redirectUri = redirectUri,
                ),
        )

        composeTestRule.clickStart()
        composeTestRule.waitForIdle()

        assertEquals(redirectUri, webNavigator.openUrl)
        verify(onFinish).invoke()
    }

    // https://govukverify.atlassian.net/browse/DCMAW-12934
    @Test
    fun `unrecoverable error loop - desktop`() {
        givenDesktopJourney()
        abortSession.result = AbortSession.Result.Error.Unrecoverable(exception = Exception("simulated"))
        getJourneyType.journeyType = JourneyType.DesktopAppDesktop

        composeTestRule.setNavGraphContent(
            startNavigatesTo = HandbackDestinations.UnrecoverableError,
        )

        composeTestRule.clickStart()
        composeTestRule.assertUnrecoverableErrorIsDisplayed()

        // Try to confirm another way and fail many times
        repeat(times = 5) {
            composeTestRule.clickUnrecoverableErrorConfirmAnotherWayButton()
            composeTestRule.assertConfirmAbortIsDisplayed()

            composeTestRule.clickConfirmAbortToDesktopWebButton()
            composeTestRule.assertUnrecoverableErrorIsDisplayed()
        }

        // Navigate back to the confirm abort screen (still within the abort modal)
        // It's the only screen on the stack within the abort modal.
        composeTestRule.goBack()
        composeTestRule.assertConfirmAbortIsDisplayed()

        // Navigate back, closing the abort modal.
        // This is the error screen we started on before we started the abort flow.
        composeTestRule.goBack()
        composeTestRule.assertUnrecoverableErrorIsDisplayed()

        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    // https://govukverify.atlassian.net/browse/DCMAW-12934
    @Test
    fun `unrecoverable error loop - mobile`() {
        givenMobileJourney()
        abortSession.result =
            AbortSession.Result.Error.Unrecoverable(exception = Exception("simulated"))
        composeTestRule.setNavGraphContent(
            startNavigatesTo = HandbackDestinations.UnrecoverableError,
        )

        composeTestRule.clickStart()
        composeTestRule.assertUnrecoverableErrorIsDisplayed()

        // Try to confirm another way and fail many times
        repeat(times = 5) {
            composeTestRule.clickUnrecoverableErrorConfirmAnotherWayButton()
            composeTestRule.assertConfirmAbortIsDisplayed()

            composeTestRule.clickConfirmAbortToMobileWebButton()
            composeTestRule.assertUnrecoverableErrorIsDisplayed()
        }

        // Navigate back to the confirm abort screen (still within the abort modal)
        // It's the only screen on the stack within the abort modal.
        composeTestRule.goBack()
        composeTestRule.assertConfirmAbortIsDisplayed()

        // Navigate back, closing the abort modal.
        // This is the error screen we started on before we started the abort flow.
        composeTestRule.goBack()
        composeTestRule.assertUnrecoverableErrorIsDisplayed()

        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `face scan limit reached - desktop`() {
        givenDesktopJourney()
        composeTestRule.setNavGraphContent(
            startNavigatesTo = HandbackDestinations.FaceScanLimitReachedDesktop,
        )

        composeTestRule.clickStart()
        composeTestRule.assertFaceScanLimitReachedDesktopIsDisplayed()

        // Back navigation is disabled
        composeTestRule.goBack()
        composeTestRule.assertFaceScanLimitReachedDesktopIsDisplayed()
    }

    @Test
    fun `face scan limit reached - mobile`() {
        val redirectUri = "http://mam-redirect-uri?state=mock-state"
        givenMobileJourney(
            redirectUri = redirectUri,
        )
        composeTestRule.setNavGraphContent(
            startNavigatesTo = HandbackDestinations.FaceScanLimitReachedMobile(REDIRECT_URI),
        )

        composeTestRule.clickStart()
        composeTestRule.assertFaceScanLimitReachedMobileIsDisplayed()

        // Back navigation is disabled
        composeTestRule.goBack()
        composeTestRule.assertFaceScanLimitReachedMobileIsDisplayed()

        composeTestRule.clickContinueToGovUkWebsite()
        composeTestRule.waitForIdle()

        assertEquals(redirectUri, webNavigator.openUrl)
    }

    private fun ComposeTestRule.assertStartIsDisplayed() =
        composeTestRule
            .onNodeWithText(START_BUTTON)
            .assertIsDisplayed()

    private fun ComposeTestRule.clickStart() =
        composeTestRule
            .onNodeWithText(START_BUTTON)
            .performClick()

    private fun ComposeTestRule.assertReturnToMobileWebIsDisplayed() =
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_returntomobileweb_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.clickContinueToGovUkWebsite() =
        composeTestRule
            .onNode(
                hasTextStartingWith(context.getString(R.string.handback_returntomobileweb_button)),
            ).assertIsDisplayed()
            .performClick()

    private fun ComposeTestRule.assertReturnToDesktopWebIsDisplayed() {
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_returntodesktopweb_title))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_returntodesktopweb_body1))
            .assertIsDisplayed()
    }

    private fun ComposeTestRule.assertUnrecoverableErrorIsDisplayed() =
        composeTestRule
            .onAllNodesWithText(context.getString(R.string.handback_unrecoverableerror_title))
            .filterInDialogElseAll()
            .onFirst()
            .assertIsDisplayed()

    private fun ComposeTestRule.clickUnrecoverableErrorConfirmAnotherWayButton() =
        composeTestRule
            .onAllNodesWithText(context.getString(R.string.handback_unrecoverableerror_button))
            .filterInDialogElseAll()
            .onFirst()
            .assertIsDisplayed()
            .performClick()

    private fun ComposeTestRule.assertConfirmAbortIsDisplayed() =
        composeTestRule
            .onAllNodesWithText(context.getString(R.string.handback_confirmabort_title))
            .filterInDialogElseAll()
            .onFirst()
            .assertIsDisplayed()

    private fun ComposeTestRule.clickConfirmAbortToDesktopWebButton() =
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_confirmabortdesktopweb_button))
            .assertIsDisplayed()
            .performClick()

    private fun ComposeTestRule.clickConfirmAbortToMobileWebButton() =
        composeTestRule
            .onNode(
                hasTextStartingWith(context.getString(R.string.handback_confirmabortmobileweb_button)),
            ).assertIsDisplayed()
            .performClick()

    private fun ComposeTestRule.assertAbortedReturnToDesktopWebIsDisplayed() {
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_abortedreturntodesktopweb_title))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_abortedreturntodesktopweb_body1))
            .assertIsDisplayed()
    }

    private fun ComposeTestRule.assertFaceScanLimitReachedDesktopIsDisplayed() {
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_facescanlimitreached_title))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_facescanlimitreached_body1))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_facescanlimitreacheddesktop_body2))
            .assertIsDisplayed()
    }

    private fun ComposeTestRule.assertFaceScanLimitReachedMobileIsDisplayed() {
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_facescanlimitreached_title))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_facescanlimitreached_body1))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.handback_facescanlimitreachedmobile_body2))
            .assertIsDisplayed()
    }

    private fun givenMobileJourney(redirectUri: String = "https://redirect-uri") {
        val session =
            Session.createMobileAppMobileInstance(
                redirectUri = redirectUri,
            )
        sessionStore.write(session)
        getJourneyType.journeyType =
            JourneyType.MobileAppMobile(redirectUri = session.redirectUri!!)
    }

    private fun givenDesktopJourney() {
        val session = Session.createDesktopAppDesktopInstance()
        sessionStore.write(session)
        getJourneyType.journeyType = JourneyType.DesktopAppDesktop
    }

    private fun givenAbortedSession() {
        isSessionAbortedOrUnavailable.state.value = true
    }

    private fun createNavGraphProvider(): HandbackNavGraphProvider =
        HandbackNavGraphProvider(
            abortModalViewModelFactory =
                AbortModalViewModelModule.provideViewModel(
                    isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
                ),
            unrecoverableErrorViewModelFactory =
                UnrecoverableErrorViewModelModule.provideFactory(
                    analytics = analytics,
                    getJourneyType = getJourneyType,
                ),
            returnToMobileViewModelFactory =
                ReturnToMobileWebViewModelModule.provideFactory(
                    analytics = analytics,
                ),
            returnToDesktopViewModelFactory =
                ReturnToDesktopWebViewModelModule.provideFactory(
                    analytics = analytics,
                    requestAppReview = requestAppReview,
                ),
            webNavigator = webNavigator,
            abortNavGraphProviders =
                persistentSetOf(
                    AbortModalNavGraphProvider(
                        confirmAbortDesktopWebViewModelFactory =
                            ConfirmAbortDesktopViewModelModule.provideFactory(
                                analytics = analytics,
                                abortSession = abortSession,
                            ),
                        confirmAbortMobileWebViewModelFactory =
                            ConfirmAbortMobileViewModelModule.provideFactory(
                                sessionStore = sessionStore,
                                analytics = analytics,
                                abortSession = abortSession,
                                logger = logger,
                            ),
                        abortedReturnToDesktopWebViewModelFactory =
                            AbortedReturnToDesktopWebViewModelModule.provideViewModel(
                                analytics = analytics,
                            ),
                        unrecoverableErrorViewModelFactory =
                            UnrecoverableErrorViewModelModule.provideFactory(
                                getJourneyType = getJourneyType,
                                analytics = analytics,
                            ),
                        webNavigator = webNavigator,
                    ),
                ),
            faceScanLimitReachedMobileViewModelFactory =
                FaceScanLimitReachedMobileViewModelModule.provideFactory(
                    analytics = analytics,
                ),
            faceScanLimitReachedDesktopViewModelFactory =
                FaceScanLimitReachedDesktopViewModelModule.provideFactory(
                    analytics = analytics,
                ),
        )

    private fun ComposeContentTestRule.setNavGraphContent(startNavigatesTo: NavigationDestination) =
        setContent {
            CompositeNavHost(
                navGraphProviders =
                    persistentSetOf(
                        InitialNavGraphProvider(navigatesTo = startNavigatesTo),
                        navGraphProvider,
                    ),
                startDestination = InitialNavGraphStart,
                onFinish = onFinish,
            )
        }
}

private const val START_BUTTON = "Start button"

@Serializable
internal data object InitialNavGraphStart : NavigationDestination

private class InitialNavGraphProvider(
    private val navigatesTo: NavigationDestination,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<InitialNavGraphStart> {
            Button(
                onClick = { navController.navigate(navigatesTo) },
            ) {
                Text(START_BUTTON)
            }
        }
    }
}
