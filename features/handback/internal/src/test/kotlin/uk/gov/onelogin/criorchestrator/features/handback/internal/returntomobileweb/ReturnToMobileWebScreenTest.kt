package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.utils.hasTextStartingWith
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import kotlin.test.assertEquals

const val REDIRECT_URI = "https://example/redirect"

@RunWith(AndroidJUnit4::class)
class ReturnToMobileWebScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val session =
        Session.createTestInstance(
            redirectUri = REDIRECT_URI,
        )
    private val viewModel =
        ReturnToMobileWebViewModel(
            analytics = mock(),
            sessionStore =
                FakeSessionStore(
                    session = session,
                ),
        )

    private val webNavigator = FakeWebNavigator()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ReturnToMobileWebScreen(
                viewModel = viewModel,
                webNavigator = webNavigator,
            )
        }
    }

    @Test
    fun `when button is clicked, it opens the gov uk website`() {
        val context: Context = ApplicationProvider.getApplicationContext()
        composeTestRule
            .onNode(
                hasTextStartingWith(context.getString(ReturnToMobileWebConstants.buttonId)),
            ).performClick()

        assertEquals(REDIRECT_URI, webNavigator.openUrl)
    }
}
