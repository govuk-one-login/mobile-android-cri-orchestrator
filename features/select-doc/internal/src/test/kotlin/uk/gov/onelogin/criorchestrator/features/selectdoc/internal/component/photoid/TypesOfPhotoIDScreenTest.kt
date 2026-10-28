package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.component.photoid

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.PreviewTypesOfPhotoIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.PreviewTypesOfPhotoIDScreenDisableBrpJourney
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.PreviewTypesOfPhotoIDScreenNoExpired
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock

@RunWith(AndroidJUnit4::class)
class TypesOfPhotoIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var ukPassportImage: SemanticsMatcher
    private lateinit var nonUKPassportImage: SemanticsMatcher
    private lateinit var brpImage: SemanticsMatcher
    private lateinit var drivingLicenceImage: SemanticsMatcher
    private lateinit var fakeSessionStore: FakeSessionStore

    private val earliestAcceptableDrivingLicenceExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(testClock())
    private val viewModel by
        lazy {
            TypesOfPhotoIDViewModel(
                analytics = mock(),
                earliestAcceptableDrivingLicenceExpiryDate = earliestAcceptableDrivingLicenceExpiryDate,
                configStore = FakeConfigStore(),
                sessionStore = fakeSessionStore,
            )
        }

    fun setUp(disableBrpJourney: Boolean) {
        initialiseMatchers(disableBrpJourney)

        composeTestRule.setContent {
            TypesOfPhotoIDScreen(
                viewModel = viewModel,
            )
        }
    }

    private fun initialiseMatchers(disableBrpJourney: Boolean) {
        val context: Context = ApplicationProvider.getApplicationContext()

        ukPassportImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_ukpassport_imagedescription))

        nonUKPassportImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_nonukpassport_imagedescription))

        brpImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_brp_imagedescription))

        drivingLicenceImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_drivinglicence_imagedescription))

        fakeSessionStore = FakeSessionStore(Session.createTestInstance(disableBrpJourney = disableBrpJourney))
    }

    @Test
    fun `images have content descriptions for talkback when disableBrpJourney flag set to false`() {
        setUp(disableBrpJourney = false)
        composeTestRule
            .onNode(hasScrollToNodeAction())
            // Scroll to each image matching by the content description
            .performScrollToNode(ukPassportImage)
            .performScrollToNode(nonUKPassportImage)
            .performScrollToNode(brpImage)
            .performScrollToNode(drivingLicenceImage)
    }

    @Test
    fun `images have content descriptions for talkback when disableBrpJourney flag set to true`() {
        setUp(disableBrpJourney = true)
        composeTestRule
            .onNode(hasScrollToNodeAction())
            // Scroll to each image matching by the content description
            .performScrollToNode(ukPassportImage)
            .performScrollToNode(nonUKPassportImage)
            .performScrollToNode(drivingLicenceImage)

        composeTestRule.onNode(brpImage).assertDoesNotExist()
    }

    @Test
    fun `test preview where enableExpiredBRP flag set and disableBrpJourney not set`() {
        initialiseMatchers(disableBrpJourney = false)
        composeTestRule.setContent {
            PreviewTypesOfPhotoIDScreen()
        }
        composeTestRule
            .onNode(hasScrollToNodeAction())
            .performScrollToNode(brpImage)
    }

    @Test
    fun `test preview where enableExpiredBRP flag not set and disableBrpJourney not set`() {
        initialiseMatchers(disableBrpJourney = false)
        composeTestRule.setContent {
            PreviewTypesOfPhotoIDScreenNoExpired()
        }
        composeTestRule
            .onNode(hasScrollToNodeAction())
            .performScrollToNode(brpImage)
    }

    @Test
    fun `test preview where enableExpiredBRP flag not set and disableBrpJourney set`() {
        initialiseMatchers(disableBrpJourney = true)
        composeTestRule.setContent {
            PreviewTypesOfPhotoIDScreenDisableBrpJourney()
        }
        composeTestRule.onNode(brpImage).assertDoesNotExist()
    }
}
