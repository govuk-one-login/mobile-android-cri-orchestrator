package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.handback.internal.DisableBackButtonTest
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.FakeWebNavigator
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.REDIRECT_URI
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

@RunWith(AndroidJUnit4::class)
class ReturnToMobileWebScreenDisableBackButtonTest : DisableBackButtonTest() {
    private val viewModel =
        ReturnToMobileWebViewModel(
            analytics = mock(),
        )

    private val webNavigator = FakeWebNavigator()

    @Before
    fun setup() {
        setContent {
            ReturnToMobileWebScreen(
                viewModel = viewModel,
                webNavigator = webNavigator,
                redirectUri = REDIRECT_URI,
            )
        }
    }
}
