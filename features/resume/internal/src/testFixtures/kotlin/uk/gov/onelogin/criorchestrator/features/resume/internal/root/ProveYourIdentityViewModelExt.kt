package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.SavedStateHandle
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubSessionReader

fun ProveYourIdentityViewModel.Companion.createTestInstance(
    sessionReader: SessionReader = StubSessionReader(),
    analytics: ResumeAnalytics = mock(),
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
) = ProveYourIdentityViewModel(
    analytics = analytics,
    sessionReader = sessionReader,
    savedStateHandle = savedStateHandle,
)
