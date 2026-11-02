package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.component.confirmnoid.nononchippedid

import android.content.Context
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.ConfirmNoNonChippedIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.ConfirmNoNonChippedIDViewModel
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.PreviewConfirmNoNonChippedIDScreenBrp
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.PreviewConfirmNoNonChippedIDScreenNoBrp
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

@RunWith(AndroidJUnit4::class)
class ConfirmNoNonChippedIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val analytics: SelectDocAnalytics = mock()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val confirmButton = hasText(context.getString(R.string.confirm_nononchippedid_confirmbutton))
    private val getJourneyType = StubGetJourneyType()
    private lateinit var fakeSessionStore: FakeSessionStore
    val brpBulletItem = hasText(context.getString(R.string.confirm_nononchippedid_bullet_1_brp))
    val noBrpBulletItem = hasText(context.getString(R.string.confirm_nononchippedid_bullet_1))
    private val viewModel by lazy {
        ConfirmNoNonChippedIDViewModel(
            analytics = analytics,
            getJourneyType = getJourneyType,
            sessionStore = fakeSessionStore,
        )
    }

    @Test
    fun `when confirm is tapped and journey is MAM, it navigates to Confirm Abort Mobile screen`() {
        fakeSessionStore = FakeSessionStore(Session.createTestInstance())
        getJourneyType.journeyType = JourneyType.MobileAppMobile("https://example.com")

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(AbortDestinations.ConfirmAbortMobile)
    }

    @Test
    fun `when confirm is tapped and journey is DAD, it navigates to Confirm Abort Desktop screen`() {
        fakeSessionStore = FakeSessionStore(Session.createTestInstance())
        getJourneyType.journeyType = JourneyType.DesktopAppDesktop

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(AbortDestinations.ConfirmAbortDesktop)
    }

    @Test
    fun `when disableBrpJourney flag is set to false, BRP bullet point is displayed`() = runTest {
        fakeSessionStore = FakeSessionStore(Session.createTestInstance())

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(brpBulletItem)
            .assertExists()
    }

    @Test
    fun `when disableBrpJourney flag is set to true, BRP bullet point is not displayed`() = runTest {
        fakeSessionStore = FakeSessionStore(Session.createTestInstance(disableBrpJourney = true))

        composeTestRule.setContent(viewModel)

        composeTestRule
            .onNode(noBrpBulletItem)
            .assertExists()
    }

    @Test
    fun `test preview when disableBrpJourney flag is set to true`() = runTest {
        composeTestRule.setContent {
            PreviewConfirmNoNonChippedIDScreenNoBrp()
        }
        composeTestRule
            .onNode(noBrpBulletItem)
            .assertExists()
    }

    @Test
    fun `test preview when disableBrpJourney flag is set to false`() = runTest {
        composeTestRule.setContent {
            PreviewConfirmNoNonChippedIDScreenBrp()
        }
        composeTestRule
            .onNode(brpBulletItem)
            .assertExists()
    }

    private fun ComposeContentTestRule.setContent(viewModel: ConfirmNoNonChippedIDViewModel) {
        composeTestRule.setContent {
            ConfirmNoNonChippedIDScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}
