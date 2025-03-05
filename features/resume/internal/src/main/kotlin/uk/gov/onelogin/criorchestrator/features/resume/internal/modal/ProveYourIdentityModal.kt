package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import uk.gov.android.ui.pages.dialog.FullScreenDialog
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
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
 */
@Composable
internal fun ProveYourIdentityModal(
    state: ProveYourIdentityModalState,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    modifier: Modifier = Modifier,
) {
    if (!state.allowedToShow) {
        return
    }

    FullScreenDialog(
        modifier = modifier,
        onDismissRequest = state::onDismissRequest,
    ) {
        CompositeNavHost(
            startDestination = ProveYourIdentityDestinations.ContinueToProveYourIdentity,
            navGraphProviders = navGraphProviders,
        )
    }
}

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
        navGraphProviders =
            persistentSetOf(
                ContinueToProveYourIdentityNavGraphProvider(),
            ),
    )
}
