package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.pages.dialog.FullScreenDialog
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
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (!state.allowedToShow) {
        return
    }

    FullScreenDialog(
        modifier = modifier,
        onDismissRequest = state::onDismissRequest,
    ) {
        content()
    }
}

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
    ) {
    }
}
