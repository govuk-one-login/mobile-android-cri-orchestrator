package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.libraries.testing.time.testClock

@RunWith(AndroidJUnit4::class)
class TypesOfPhotoIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var ukPassportImage: SemanticsMatcher
    private lateinit var nonUKPassportImage: SemanticsMatcher
    private lateinit var brpImage: SemanticsMatcher
    private lateinit var drivingLicenceImage: SemanticsMatcher

    private val earliestAcceptableDrivingLicenceExpiryDate = EarliestAcceptableDrivingLicenceExpiryDate(testClock())
    private val viewModel =
        spy(
            TypesOfPhotoIDViewModel(
                analytics = mock(),
                earliestAcceptableDrivingLicenceExpiryDate = earliestAcceptableDrivingLicenceExpiryDate,
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()

        ukPassportImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_ukpassport_imagedescription))

        nonUKPassportImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_nonukpassport_imagedescription))

        brpImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_brp_imagedescription))

        drivingLicenceImage =
            hasContentDescription(context.getString(R.string.typesofphotoid_drivinglicence_imagedescription))

        composeTestRule.setContent {
            TypesOfPhotoIDScreen(
                viewModel = viewModel,
            )
        }
    }

    @Test
    fun `images have content descriptions for talkback`() {
        composeTestRule
            .onNode(hasScrollToNodeAction())
            // Scroll to each image matching by the content description
            .performScrollToNode(ukPassportImage)
            .performScrollToNode(nonUKPassportImage)
            .performScrollToNode(brpImage)
            .performScrollToNode(drivingLicenceImage)
    }
}
