package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.unit.passport.select

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select.SelectPassportAction
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select.SelectPassportViewModel
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class SelectPassportViewModelTest {
    private val analyticsLogger = mock<SelectDocAnalytics>()
    private var fakeSessionStore: FakeSessionStore = FakeSessionStore(Session.createTestInstance())

    private val viewModel by lazy {
        SelectPassportViewModel(
            analytics = analyticsLogger,
            sessionStore = fakeSessionStore,
        )
    }

    // DCMAW-8054 | AC1: Viewing passport screen
    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.SelectPassport,
                title = R.string.selectdocument_passport_title,
            )
    }

    // DCMAW-8054 | AC2: User has a passport
    // DCMAW-8054 | AC3: User doesn’t have a passport
    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when selection is confirmed, it sends analytics`(selection: Int) {
        viewModel.onConfirmSelection(selection)

        val item =
            listOf(
                R.string.selectdocument_passport_selection_yes,
                R.string.selectdocument_passport_selection_no,
            )[selection]

        verify(analyticsLogger)
            .trackFormSubmission(
                R.string.selectdocument_passport_continuebutton,
                item,
            )
    }

    @Test
    fun `when passport is selected, navigate to confirmation`() {
        runTest {
            viewModel.actions.test {
                viewModel.onConfirmSelection(0)

                assertEquals(SelectPassportAction.NavigateToConfirmation, awaitItem())
            }
        }
    }

    @Test
    fun `when passport is not selected and session disableBrpJourney flag is false, navigate to BRP`() {
        runTest {
            viewModel.actions.test {
                viewModel.onConfirmSelection(1)

                assertEquals(SelectPassportAction.NavigateToBrp, awaitItem())
            }
        }
    }

    @Test
    fun `when passport is not selected and session disableBrpJourney flag is true, navigate to DL`() {
        runTest {
            val session = Session(sessionId = "abc1234", disableBrpJourney = true)
            fakeSessionStore = FakeSessionStore(session)
            viewModel.actions.test {
                viewModel.onConfirmSelection(1)

                assertEquals(SelectPassportAction.NavigateToDrivingLicence, awaitItem())
            }
        }
    }

    @Test
    fun `when the read more button is pressed, it sends analytics`() {
        viewModel.onReadMoreClick()

        verify(analyticsLogger)
            .trackButtonEvent(R.string.selectdocument_passport_readmore_button)
    }

    // DCMAW-8054 | AC8: User wants to learn about different document options
    @Test
    fun `when the read more button is pressed, the user sees the types of photo ID screen`() {
        runTest {
            viewModel.actions.test {
                viewModel.onReadMoreClick()

                assertEquals(SelectPassportAction.NavigateToTypesOfPhotoID, awaitItem())
            }
        }
    }
}
