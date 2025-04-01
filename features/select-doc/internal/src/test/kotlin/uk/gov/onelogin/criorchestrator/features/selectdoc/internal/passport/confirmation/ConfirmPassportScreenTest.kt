package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.IdCheckWrapperDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

@RunWith(AndroidJUnit4::class)
class ConfirmPassportScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var image: SemanticsMatcher
    private lateinit var confirmButton: SemanticsMatcher

    private val navController: NavController = mock()

    private val viewModel =
        spy(
            ConfirmPassportViewModel(
                analytics = mock(),
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        image =
            hasContentDescription(context.getString(R.string.selectdocument_passport_imagedescription))
        confirmButton = hasText(context.getString(R.string.confirmdocument_confirmbutton))

        composeTestRule.setContent {
            ConfirmPassportScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when talkback is enabled, the image description is read correctly`() {
        composeTestRule
            .onNode(image)
            .assertExists()
    }

    @Test
    fun `when confirm is tapped, it navigates to sync SDK screen`() {
        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(
            IdCheckWrapperDestinations.SyncIdCheckScreen(
                DocumentType.NFC_PASSPORT,
            ),
        )
    }
}
