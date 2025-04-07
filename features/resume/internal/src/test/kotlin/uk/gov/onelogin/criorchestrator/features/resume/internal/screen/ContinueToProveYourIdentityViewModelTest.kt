
package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.v3dot1.logger.logEventV3Dot1
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeScreenId
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModel.ContinueToProveYourIdentityAction
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.FakeResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import kotlin.test.assertEquals

@ExtendWith(MainDispatcherExtension::class)
class ContinueToProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val resourceProvider = FakeResourceProvider()
    private val nfcChecker: NfcChecker = mock()
    private val viewModel: ContinueToProveYourIdentityViewModel by lazy {
        ContinueToProveYourIdentityViewModel(
            analytics =
                ResumeAnalytics(
                    resourceProvider = resourceProvider,
                    analyticsLogger = analyticsLogger,
                ),
            nfcChecker = nfcChecker,
        )
    }

    @Test
    fun `when continue button is clicked, it sends analytics`() {
        viewModel.onContinueClick()

        val expectedEvent: AnalyticsEvent =
            TrackEvent.Button(
                text = resourceProvider.defaultEnglishString,
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            )
        verify(analyticsLogger).logEventV3Dot1(expectedEvent)
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        val expectedEvent: AnalyticsEvent =
            ViewEvent.Screen(
                id = ResumeScreenId.ContinueToProveYourIdentity.rawId,
                name = resourceProvider.defaultEnglishString,
                params =
                    RequiredParameters(
                        taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                        taxonomyLevel3 = TaxonomyLevel3.RESUME,
                    ),
            )
        verify(analyticsLogger).logEventV3Dot1(expectedEvent)
    }

    @Test
    fun `given nfc available, when continue clicked, navigate to passport`() =
        runTest {
            whenever(nfcChecker.hasNfc()).thenReturn(true)

            viewModel.actions.test {
                viewModel.onContinueClick()

                assertEquals(ContinueToProveYourIdentityAction.NavigateToPassport, awaitItem())
            }
        }

    @Test
    fun `given nfc not available, when continue clicked, navigate to driving license`() =
        runTest {
            whenever(nfcChecker.hasNfc()).thenReturn(false)

            viewModel.actions.test {
                viewModel.onContinueClick()

                assertEquals(ContinueToProveYourIdentityAction.NavigateToDrivingLicense, awaitItem())
            }
        }
}
