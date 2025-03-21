package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
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
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeScreenId
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.FakeResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherExtension
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ContinueToProveYourIdentityViewModelTest {
    private val analyticsLogger = mock<AnalyticsLogger>()
    private val resourceProvider = FakeResourceProvider()
    private val nfcChecker: NfcChecker = mock()
    private val configStore: ConfigStore = mock()
    private val viewModel: ContinueToProveYourIdentityViewModel by lazy {
        ContinueToProveYourIdentityViewModel(
            analytics =
                ResumeAnalytics(
                    resourceProvider = resourceProvider,
                    analyticsLogger = analyticsLogger,
                ),
            nfcChecker = nfcChecker,
            configStore,
        )
    }

    @RegisterExtension
    val dispatcherExtension = MainStandardDispatcherExtension()

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
    fun `when continue clicked and nfc available, NfcAvailable is emitted`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(false)),
            )

            whenever(nfcChecker.hasNfc()).thenReturn(true)

            viewModel.onContinueClick()

            dispatcherExtension.mainDispatcher.scheduler.advanceUntilIdle()

            assertEquals(ProveYourIdentityState.NfcAvailable, viewModel.state.first())
        }

    @Test
    fun `when continue clicked and nfc not available, NfcNotAvailable is emitted`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(false)),
            )

            whenever(nfcChecker.hasNfc()).thenReturn(false)

            viewModel.onContinueClick()

            dispatcherExtension.mainDispatcher.scheduler.advanceUntilIdle()

            assertEquals(ProveYourIdentityState.NfcNotAvailable, viewModel.state.first())
        }

    @Test
    fun `when continue clicked and nfc not available, NfcAvailable not emitted`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(false)),
            )

            whenever(nfcChecker.hasNfc()).thenReturn(false)

            viewModel.onContinueClick()

            dispatcherExtension.mainDispatcher.scheduler.advanceUntilIdle()

            assertNotEquals(ProveYourIdentityState.NfcAvailable, viewModel.state.first())
        }

    @Test
    fun `when continue clicked and nfc is available, NfcNotAvailable not emitted`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(false)),
            )

            whenever(nfcChecker.hasNfc()).thenReturn(true)

            viewModel.onContinueClick()

            dispatcherExtension.mainDispatcher.scheduler.advanceUntilIdle()

            assertNotEquals(ProveYourIdentityState.NfcNotAvailable, viewModel.state.first())
        }

    @Test
    fun `when continue clicked and stub nfc check is enabled and nfc config is set to available`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(true)),
            )

            whenever(configStore.read(NfcConfigKey.IsNfcAvailable)).thenReturn(
                flowOf(Config.Value.BooleanValue(true)),
            )

            viewModel.onContinueClick()

            dispatcherExtension.mainDispatcher.scheduler.advanceUntilIdle()

            assertEquals(ProveYourIdentityState.NfcAvailable, viewModel.state.first())

            verify(nfcChecker, never()).hasNfc()
        }

    @Test
    fun `when continue clicked and stub nfc check is enabled and nfc config is set to not available`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(true)),
            )

            whenever(configStore.read(NfcConfigKey.IsNfcAvailable)).thenReturn(
                flowOf(Config.Value.BooleanValue(false)),
            )

            viewModel.onContinueClick()

            dispatcherExtension.mainDispatcher.scheduler.advanceUntilIdle()

            assertEquals(ProveYourIdentityState.NfcNotAvailable, viewModel.state.first())

            verify(nfcChecker, never()).hasNfc()
        }

    @Test
    fun `when continue clicked and stub nfc check is not enabled`() =
        runTest {
            whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
                flowOf(Config.Value.BooleanValue(false)),
            )

            verify(configStore, never()).read(NfcConfigKey.IsNfcAvailable)
        }
}
