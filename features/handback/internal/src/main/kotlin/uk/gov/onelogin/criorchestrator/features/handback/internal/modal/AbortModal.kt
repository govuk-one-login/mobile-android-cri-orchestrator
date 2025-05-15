package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.patterns.dialog.FullScreenDialogue
import uk.gov.android.ui.patterns.dialog.FullScreenDialogueTopAppBar
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbortModal(
    abortModalViewModel: AbortModalViewModel,
    startDestination: AbortDestinations,
    navGraphProviders: ImmutableSet<AbortNavGraphProvider>,
    onDismissRequest: () -> Unit,
    onFinish: () -> Unit,
) {
    val isAbortedState = abortModalViewModel.isAborted.collectAsState(false)

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
