package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.onelogin.criorchestrator.features.resume.internal.card.ProveYourIdentityUiCard
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModal
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.rememberProveYourIdentityModalState
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider

@Composable
internal fun ProveYourIdentityRoot(
    viewModel: ProveYourIdentityViewModel,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    val modalState =
        rememberProveYourIdentityModalState(
            initiallyAllowedToShow = state.shouldDisplay,
        )

    if (state.shouldDisplay) {
        ProveYourIdentityUiCard(
            onStartClick = {
                viewModel.start()
                modalState.allowToShow()
            },
            modifier =
                modifier
                    .testTag(ProveYourIdentityRootTestTags.CARD),
        )
    }

    ProveYourIdentityModal(
        state = modalState,
        navGraphProviders = navGraphProviders,
        modifier =
            Modifier
                .testTag(ProveYourIdentityRootTestTags.MODAL),
    )
}

internal object ProveYourIdentityRootTestTags {
    internal const val MODAL = "ProveIdentityRootModalTestTag"
    internal const val CARD = "ProveIdentityRootCardTestTag"
}
