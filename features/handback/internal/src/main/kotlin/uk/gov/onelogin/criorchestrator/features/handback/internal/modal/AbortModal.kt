package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.patterns.dialog.FullScreenDialogue
import uk.gov.android.ui.patterns.dialog.FullScreenDialogueTopAppBar
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbortModal(
    startDestination: AbortDestinations,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    onDismiss: () -> Unit,
) {
    FullScreenDialogue(
        onDismissRequest = onDismiss,
        topAppBar = {
            FullScreenDialogueTopAppBar(onCloseClick = onDismiss)
        },
    ) {
        AbortModalNavHost(
            startDestination = startDestination,
            navGraphProviders = navGraphProviders,
        )
    }
}
