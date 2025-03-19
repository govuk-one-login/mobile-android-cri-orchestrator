package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.componentsv2.button.CloseButton
import uk.gov.android.ui.patterns.dialog.FullScreenDialog
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.CompositeNavHost

/**
 * A modal dialog that allows a user to prove their identity.
 *
 * If it is allowed to, this dialog will display automatically.
 *
 * @param state The modal UI state.
 * @param modifier See [Modifier].
 * @param content The modal content (see [ProveYourIdentityModalNavHost])
 */
@Composable
internal fun ProveYourIdentityModal(
    state: ProveYourIdentityModalState,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (!state.allowedToShow) {
        return
    }

    FullScreenDialog(
        modifier = modifier,
        topAppBar = {
            FullScreenDialogTopAppBar(
                onCancelClick = {
                    onCancelClick()
                    state.onDismissRequest()
                },
            )
        },
        onDismissRequest = state::onDismissRequest,
    ) {
        content()
    }
}

// TODO Move this to `mobile-android-ui`
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FullScreenDialogTopAppBar(onCancelClick: () -> Unit) =
    TopAppBar(
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.background,
            ),
        title = { },
        navigationIcon = {
            CloseButton(onClose = onCancelClick)
        },
    )

@Composable
internal fun ProveYourIdentityModalNavHost(
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    modifier: Modifier = Modifier,
) = CompositeNavHost(
    startDestination = ProveYourIdentityDestinations.ContinueToProveYourIdentity,
    navGraphProviders = navGraphProviders,
    modifier = modifier,
)

internal data class PreviewParams(
    val state: ProveYourIdentityModalState,
)

@Suppress("MaxLineLength") // Conflict between Ktlint formatting and Detekt rule
internal class ProveYourIdentityModalPreviewParameterProvider : PreviewParameterProvider<PreviewParams> {
    override val values =
        sequenceOf(
            PreviewParams(
                state = ProveYourIdentityModalState(allowedToShow = true),
            ),
            PreviewParams(
                state = ProveYourIdentityModalState(allowedToShow = false),
            ),
        )
}

@PreviewLightDark
@Composable
internal fun ProveYourIdentityModalPreview(
    @PreviewParameter(ProveYourIdentityModalPreviewParameterProvider::class)
    parameters: PreviewParams,
) = GdsTheme {
    ProveYourIdentityModal(
        state = parameters.state,
        onCancelClick = {},
    ) {
    }
}
