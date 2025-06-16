package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.patterns.dialog.FullScreenDialogueTopAppBar
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbortModal(
    abortModalViewModel: AbortModalViewModel,
    startDestination: AbortDestinations,
    navGraphProviders: ImmutableSet<AbortNavGraphProvider>,
    onDismissRequest: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isAbortedState = abortModalViewModel.isAborted.collectAsState(false)

    val handleDismissRequest = {
        if (isAbortedState.value) {
            onFinish()
        } else {
            onDismissRequest()
        }
    }

    BackHandler {
        handleDismissRequest()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            FullScreenDialogueTopAppBar(
                onCloseClick = handleDismissRequest,
            )
            AbortModalNavHost(
                startDestination = startDestination,
                navGraphProviders = navGraphProviders,
                onFinish = onFinish,
            )
        }
    }
}
