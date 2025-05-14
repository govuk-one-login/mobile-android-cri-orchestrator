package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.patterns.dialog.FullScreenDialogue
import uk.gov.android.ui.patterns.dialog.FullScreenDialogueTopAppBar
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbortModal(
    abortViewModel: AbortViewModel,
    startDestination: AbortDestinations,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    onDismissRequest: () -> Unit,
    onFinish: () -> Unit,
) {
    val isAbortedState = abortViewModel.isAborted.collectAsState(false)

    val handleDismissRequest = {
        if (isAbortedState.value) {
            onFinish()
        } else {
            onDismissRequest()
        }
    }

    FullScreenDialogue(
        onDismissRequest = handleDismissRequest,
        topAppBar = {
            FullScreenDialogueTopAppBar(onCloseClick = handleDismissRequest)
        },
    ) {
        AbortModalNavHost(
            startDestination = startDestination,
            navGraphProviders = navGraphProviders,
            onFinish = onFinish,
        )
    }
}
