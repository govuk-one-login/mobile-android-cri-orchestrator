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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.card.ProveYourIdentityUiCard
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModal
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModalNavHost
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.composeutils.fullScreenDialogProperties

@Composable
internal fun ProveYourIdentityRoot(
    viewModel: ProveYourIdentityViewModel,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        launch {
            viewModel.actions
                .onSubscription {
                    // Ensure we've started collecting actions before starting
                    viewModel.onScreenStart()
                }.collect {
                    when (it) {
                        ProveYourIdentityRootUiAction.AllowModalToShow ->
                            navController.navigate(DESTINATION_MODAL)
                    }
                }
        }
    }

    val onCardStartClick = {
        viewModel.onStartClick()
        navController.navigate(DESTINATION_MODAL)
    }

    ProveYourIdentityRootNavHost(
        state = state,
        navController = navController,
        onCardStartClick = onCardStartClick,
        onModalCancelClick = viewModel::onModalCancelClick,
        onModalDismissRequest = navController::popBackStack,
        modifier = modifier,
        modalContent = {
            ProveYourIdentityModalNavHost(
                navGraphProviders = navGraphProviders,
                onFinish = navController::popBackStack,
            )
        },
    )
}

private const val DESTINATION_CARD = "/card"
private const val DESTINATION_MODAL = "/modal"

@Suppress("LongParameterList")
@Composable
internal fun ProveYourIdentityRootNavHost(
    state: ProveYourIdentityRootUiState,
    navController: NavHostController,
    onCardStartClick: () -> Unit,
    modifier: Modifier = Modifier,
    onModalCancelClick: () -> Unit = {},
    onModalDismissRequest: () -> Unit = {},
    // Suppress naming rule for clarity
    @SuppressLint("ComposableLambdaParameterNaming")
    modalContent: @Composable () -> Unit,
) =
    NavHost(
        navController = navController,
        startDestination = DESTINATION_CARD,
    ) {
        composable(DESTINATION_CARD) {
            if (state.showCard) {
                ProveYourIdentityUiCard(
                    onStartClick = onCardStartClick,
                    modifier =
                        modifier
                            .testTag(ProveYourIdentityRootTestTags.CARD),
                )
            }
        }

        dialog(
            DESTINATION_MODAL,
            dialogProperties = fullScreenDialogProperties,
        ) {
            ProveYourIdentityModal(
                onCancelClick = onModalCancelClick,
                onDismissRequest = onModalDismissRequest,
                modifier = Modifier.testTag(ProveYourIdentityRootTestTags.MODAL),
            ) {
                modalContent()
            }
        }
    }

internal data class PreviewParams(
    val state: ProveYourIdentityRootUiState,
)

@Suppress("MaxLineLength") // Conflict between Ktlint formatting and Detekt rule
internal class ProveYourIdentityRootNavHostPreviewParameterProvider : PreviewParameterProvider<PreviewParams> {
    override val values =
        sequenceOf(
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = true),
            ),
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = false),
            ),
        )
}

@Composable
@PreviewLightDark
internal fun ProveYourIdentityRootContentPreview(
    @PreviewParameter(ProveYourIdentityRootNavHostPreviewParameterProvider::class)
    parameters: PreviewParams,
) {
    GdsTheme {
        ProveYourIdentityRootNavHost(
            state = parameters.state,
            onCardStartClick = {},
            navController = rememberNavController(),
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
