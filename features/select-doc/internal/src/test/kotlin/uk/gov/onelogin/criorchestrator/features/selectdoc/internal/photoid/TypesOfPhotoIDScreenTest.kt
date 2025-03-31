package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

@RunWith(AndroidJUnit4::class)
class TypesOfPhotoIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var ukPassportImage: SemanticsMatcher
    private lateinit var nonUKPassportImage: SemanticsMatcher
    private lateinit var brpImage: SemanticsMatcher
    private lateinit var drivingLicenceImage: SemanticsMatcher

    private val viewModel =
        spy(
            TypesOfPhotoIDViewModel(
                analytics = mock(),
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
    fun `when talkback is enabled, the image description is read correctly`() {
        composeTestRule
            .onNode(ukPassportImage)
            .assertExists()

        composeTestRule
            .onNode(nonUKPassportImage)
            .assertExists()

        swipeToAdditionalContent()

        composeTestRule
            .onNode(brpImage)
            .assertExists()

        composeTestRule
            .onNode(drivingLicenceImage)
            .assertExists()
    }

    private fun swipeToAdditionalContent() {
        composeTestRule
            .onNode(nonUKPassportImage)
            .onParent()
            .performTouchInput {
                swipeUp()
            }
    }
}
