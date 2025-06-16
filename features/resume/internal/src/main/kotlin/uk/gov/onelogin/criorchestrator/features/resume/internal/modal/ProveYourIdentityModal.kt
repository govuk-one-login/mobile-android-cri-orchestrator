package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.patterns.dialog.FullScreenDialogueTopAppBar
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.CompositeNavHost

/**
 * A modal dialog that allows a user to prove their identity.
 *
 * If it is allowed to, this dialog will display automatically.
 *
 * @param onDismissRequest Executes when the user tries to dismiss the modal.
 * @param onCancelClick Executes when the user clicks the close/cancel button.
 *   Dismissing the modal should be handled separately using [onDismissRequest].
 * @param modifier See [Modifier].
 * @param content The modal content (see [ProveYourIdentityModalNavHost])
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProveYourIdentityModal(
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            FullScreenDialogueTopAppBar(
                onCloseClick = {
                    onCancelClick()
                    onDismissRequest()
                },
            )
            content()
        }
    }
}

@Composable
internal fun ProveYourIdentityModalNavHost(
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositeNavHost(
        startDestination = ProveYourIdentityDestinations.ContinueToProveYourIdentity,
        navGraphProviders = navGraphProviders,
        onFinish = onFinish,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
internal fun ProveYourIdentityModalPreview() =
    GdsTheme {
        ProveYourIdentityModal(
            onDismissRequest = {},
            onCancelClick = {},
        ) {
        }
    }
