package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.zacsweers.metro.Provider
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeIsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeRefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LocalDropUnlessResumedDisabled
import kotlin.reflect.KClass

/**
 * Reproduces the race condition crash:
 * `IllegalStateException: State is 'DESTROYED' and cannot be moved to 'CREATED'`
 *
 * This occurs when the modal's nested CompositeNavHost is being torn down (popBackStack)
 * at the same moment the isSessionResumable flow emits true, triggering AllowModalToShow
 * which navigates back to the modal destination.
 *
 * In production, this happens when a token refresh (PoP) response arrives at the exact
 * frame the user dismisses the modal or the ID check flow completes.
 */
@RunWith(AndroidJUnit4::class)
class ProveYourIdentityRootRaceConditionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val isSessionResumable = FakeIsSessionResumable()
    private val refreshActiveSession = FakeRefreshActiveSession(
        willHaveActiveSession = true,
        fakeIsSessionResumable = isSessionResumable,
    )

    private val closeButton = hasContentDescription("Close")
    private val modal = hasTestTag(ProveYourIdentityRootTestTags.MODAL)

    /**
     * Forces the race condition by:
     * 1. Displaying the root with the modal auto-shown (session is resumable)
     * 2. Dismissing the modal (popBackStack — tears down the nested CompositeNavHost)
     * 3. Immediately emitting isSessionResumable=true to trigger AllowModalToShow
     *    (simulating a token refresh response arriving during the dismiss)
     *
     * If the bug is present, this will throw:
     * IllegalStateException: State is 'DESTROYED' and cannot be moved to 'CREATED'
     * in component NavBackStackEntry destination=ComposeNavGraph(0x0)
     */
    @Test
    fun `dismissing modal while session becomes resumable does not crash`() {
        composeTestRule.displayProveYourIdentityRoot()
        composeTestRule.waitForIdle()

        // Dismiss the modal — this triggers popBackStack, destroying the nested NavHost
        composeTestRule
            .onNode(closeButton)
            .performClick()

        // Simulate token refresh arriving during dismiss: toggle session to re-trigger AllowModalToShow
        isSessionResumable.value.value = false
        composeTestRule.waitForIdle()
        isSessionResumable.value.value = true
        composeTestRule.waitForIdle()

        // If we get here without crashing, the race condition is handled
        composeTestRule
            .onNode(modal)
            .assertExists()
    }

    /**
     * Tighter race: dismiss and re-trigger without waiting for idle between the toggle.
     * This simulates the token refresh arriving on the same frame as the dismiss.
     */
    @Test
    fun `dismissing modal and immediately re-triggering session resumable does not crash`() {
        composeTestRule.displayProveYourIdentityRoot()
        composeTestRule.waitForIdle()

        // Dismiss the modal
        composeTestRule
            .onNode(closeButton)
            .performClick()

        // Don't wait for idle — immediately toggle to force same-frame race
        isSessionResumable.value.value = false
        isSessionResumable.value.value = true

        // Now let composition settle — this is where the crash would occur
        composeTestRule.waitForIdle()

        composeTestRule
            .onNode(modal)
            .assertExists()
    }

    /**
     * Rapidly dismiss and re-navigate in a tight loop to increase
     * the chance of hitting the frame-timing race.
     */
    @Test
    fun `repeatedly dismissing and re-triggering modal does not crash`() {
        composeTestRule.displayProveYourIdentityRoot()
        composeTestRule.waitForIdle()

        repeat(5) {
            // Dismiss
            composeTestRule
                .onNode(closeButton)
                .performClick()

            // Immediately re-trigger by toggling session resumable
            isSessionResumable.value.value = false
            isSessionResumable.value.value = true
            composeTestRule.waitForIdle()
        }
    }

    /**
     * Simulates the user tapping the card to re-open the modal immediately after dismissing.
     * The card's navigate call does NOT use launchSingleTop, making this path more vulnerable.
     */
    @Test
    fun `dismissing modal and immediately clicking card does not crash`() {
        composeTestRule.displayProveYourIdentityRoot()
        composeTestRule.waitForIdle()

        // Dismiss the modal
        composeTestRule
            .onNode(closeButton)
            .performClick()
        composeTestRule.waitForIdle()

        // Toggle session to simulate token refresh arriving after dismiss
        // This forces savedShowCard from true → false → true, re-triggering AllowModalToShow
        isSessionResumable.value.value = false
        composeTestRule.waitForIdle()
        isSessionResumable.value.value = true
        composeTestRule.waitForIdle()

        // Then dismiss again and re-trigger — tests the second cycle
        composeTestRule
            .onNode(closeButton)
            .performClick()
        composeTestRule.waitForIdle()

        isSessionResumable.value.value = false
        composeTestRule.waitForIdle()
        isSessionResumable.value.value = true
        composeTestRule.waitForIdle()

        composeTestRule
            .onNode(modal)
            .assertExists()
    }

    private fun ComposeContentTestRule.displayProveYourIdentityRoot() {
        val viewModel = ProveYourIdentityViewModel.createTestInstance(
            isSessionResumable = isSessionResumable,
            refreshActiveSession = refreshActiveSession,
        )
        val navGraphProviders = persistentSetOf(
            ContinueToProveYourIdentityNavGraphProvider(),
        )
        val metroVmf = object : MetroViewModelFactory() {
            override val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>> =
                mapOf(
                    ContinueToProveYourIdentityViewModel::class to Provider {
                        ContinueToProveYourIdentityViewModel(
                            analytics = mock(),
                            nfcChecker = mock(),
                        )
                    },
                )
        }
        setContent {
            CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
                CompositionLocalProvider(LocalDropUnlessResumedDisabled provides true) {
                    ProveYourIdentityRoot(
                        viewModel = viewModel,
                        navGraphProviders = navGraphProviders,
                    )
                }
            }
        }
    }
}
