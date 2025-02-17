package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import uk.gov.onelogin.criorchestrator.features.resume.internal.card.ProveYourIdentityUiCard
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModal
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.rememberProveYourIdentityModalState

@Composable
internal fun ProveYourIdentityRoot(
    viewModel: ProveYourIdentityViewModel,
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
        modifier =
            Modifier
                .testTag(ProveYourIdentityRootTestTags.MODAL),
    )
}

internal object ProveYourIdentityRootTestTags {
    internal const val MODAL = "ProveIdentityRootModalTestTag"
    internal const val CARD = "ProveIdentityRootCardTestTag"
}
