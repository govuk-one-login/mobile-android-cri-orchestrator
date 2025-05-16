package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubIsSessionAbortedOrUnavailable

@RunWith(AndroidJUnit4::class)
class AbortModalTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var hasCalledOnFinished = false
    private var hasCalledOnDismissed = false

    @After
    fun tearDown() {
        hasCalledOnFinished = false
        hasCalledOnDismissed = false
    }

    @Test
    fun `when session is available, dismissing dialog goes calls onDismissRequest`() {
        val isSessionAbortedOrUnavailable =
            StubIsSessionAbortedOrUnavailable(
                false,
            )
        val abortModalViewModel =
            AbortModalViewModel(
                isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
            )
        composeTestRule.setContent {
            AbortModal(
                abortModalViewModel = abortModalViewModel,
                startDestination = AbortDestinations.ConfirmAbortMobile,
                navGraphProviders = persistentSetOf(FakeAbortModalNavGraph.Provider()),
                onDismissRequest = ::onDismissRequest,
                onFinish = ::onFinish,
            )
        }

        composeTestRule.onNodeWithContentDescription("Close").performClick()

        assertTrue("hasCalledOnDismissed should be true!", hasCalledOnDismissed)
        assertFalse("hasCalledOnFinished should be false!", hasCalledOnFinished)
    }

    @Test
    fun `when session is not available, dismissing dialog goes calls onFinish`() {
        val isSessionAbortedOrUnavailable =
            StubIsSessionAbortedOrUnavailable(
                true,
            )
        val abortModalViewModel =
            AbortModalViewModel(
                isSessionAbortedOrUnavailable = isSessionAbortedOrUnavailable,
            )
        composeTestRule.setContent {
            AbortModal(
                abortModalViewModel = abortModalViewModel,
                startDestination = AbortDestinations.ConfirmAbortMobile,
                navGraphProviders = persistentSetOf(FakeAbortModalNavGraph.Provider()),
                onDismissRequest = ::onDismissRequest,
                onFinish = ::onFinish,
            )
        }

        composeTestRule.onNodeWithContentDescription("Close").performClick()

        assertTrue("hasCalledOnFinished should be true!", hasCalledOnFinished)
        assertFalse("hasCalledOnDismissed should be false!", hasCalledOnDismissed)
    }

    private fun onDismissRequest() {
        hasCalledOnDismissed = true
    }

    private fun onFinish() {
        hasCalledOnFinished = true
    }
}
