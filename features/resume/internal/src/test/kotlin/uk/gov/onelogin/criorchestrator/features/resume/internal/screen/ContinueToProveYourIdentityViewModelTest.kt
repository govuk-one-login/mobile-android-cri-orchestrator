package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
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
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.FakeResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class ContinueToProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val resourceProvider = FakeResourceProvider()
    private val nfcChecker: NfcChecker = mock()
    private val testDispatcher = StandardTestDispatcher()

    private val viewModel by lazy {
        ContinueToProveYourIdentityViewModel(
            analytics =
                ResumeAnalytics(
                    resourceProvider = resourceProvider,
                    analyticsLogger = analyticsLogger,
                ),
            nfcChecker = nfcChecker,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
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
    fun `when continue clicked and nfc available`() =
        runTest {
            whenever(nfcChecker.hasNfc()).thenReturn(true)

            viewModel.onContinueClick()

            testDispatcher.scheduler.advanceUntilIdle()

            TestCase.assertEquals(ProveYourIdentityState.NfcAvailable, viewModel.state.first())
        }

    @Test
    fun `when continue clicked and nfc not available`() =
        runTest {
            whenever(nfcChecker.hasNfc()).thenReturn(false)

            viewModel.onContinueClick()

            testDispatcher.scheduler.advanceUntilIdle()

            TestCase.assertEquals(ProveYourIdentityState.NfcNotAvailable, viewModel.state.first())
        }
}
