package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavHostController
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.card.ProveYourIdentityUiCard
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModal
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModalNavHost
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModalState
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.rememberProveYourIdentityModalState
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider

@Composable
internal fun ProveYourIdentityRoot(
    viewModel: ProveYourIdentityViewModel,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val modalState = rememberProveYourIdentityModalState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.actions
                .onSubscription {
                    // Ensure we've started collecting actions before starting
                    viewModel.onScreenStart()
                }.collect {
                    when (it) {
                        ProveYourIdentityRootUiAction.AllowModalToShow -> modalState.allowToShow()
                    }
                }
        }
    }

    val onCardStartClick = {
        viewModel.onStartClick()
        modalState.allowToShow()
    }

    ProveYourIdentityRootContent(
        state = state,
        onCardStartClick = onCardStartClick,
        onModalCancelClick = viewModel::onModalCancelClick,
        modalState = modalState,
        modifier = modifier,
        modalContent = {
            ProveYourIdentityModalNavHost(
                navController = navController,
                navGraphProviders = navGraphProviders,
                onFinish = { modalState.onDismissRequest() },
            )
        },
    )
}

@Suppress("LongParameterList")
@Composable
internal fun ProveYourIdentityRootContent(
    state: ProveYourIdentityRootUiState,
    onCardStartClick: () -> Unit,
    modalState: ProveYourIdentityModalState,
    modifier: Modifier = Modifier,
    onModalCancelClick: () -> Unit = {},
    // Suppress naming rule for clarity
    @SuppressLint("ComposableLambdaParameterNaming")
    modalContent: @Composable () -> Unit,
) {
    if (state.showCard) {
        ProveYourIdentityUiCard(
            onStartClick = onCardStartClick,
            modifier =
                modifier
                    .testTag(ProveYourIdentityRootTestTags.CARD),
        )
    }

    ProveYourIdentityModal(
        state = modalState,
        onCancelClick = onModalCancelClick,
        modifier = Modifier.testTag(ProveYourIdentityRootTestTags.MODAL),
    ) {
        modalContent()
    }
}

internal data class PreviewParams(
    val state: ProveYourIdentityRootUiState,
    val modalState: ProveYourIdentityModalState,
)

@Suppress("MaxLineLength") // Conflict between Ktlint formatting and Detekt rule
internal class ProveYourIdentityRootContentPreviewParameterProvider : PreviewParameterProvider<PreviewParams> {
    override val values =
        sequenceOf(
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = true),
                modalState = ProveYourIdentityModalState(allowedToShow = true),
            ),
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = true),
                modalState = ProveYourIdentityModalState(allowedToShow = false),
            ),
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = false),
                modalState = ProveYourIdentityModalState(allowedToShow = false),
            ),
        )
}

@Composable
@PreviewLightDark
internal fun ProveYourIdentityRootContentPreview(
    @PreviewParameter(ProveYourIdentityRootContentPreviewParameterProvider::class)
    parameters: PreviewParams,
) {
    GdsTheme {
        ProveYourIdentityRootContent(
            state = parameters.state,
            onCardStartClick = {},
            modalState = parameters.modalState,
            modalContent = {
                Text("Prove your identity modal is open")
            },
        )
    }
}

internal object ProveYourIdentityRootTestTags {
    internal const val MODAL = "ProveIdentityRootModalTestTag"
    internal const val CARD = "ProveIdentityRootCardTestTag"
}
