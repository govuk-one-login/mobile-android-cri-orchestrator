package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.android.ui.patterns.dialog.FullScreenDialogue
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityNavGraphProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnableToConfirmIdentityModal(
    startDestination: UnableToConfirmIdentityDestinations,
    navGraphProviders: ImmutableSet<UnableToConfirmIdentityNavGraphProvider>,
    onFinish: () -> Unit,
) {
    FullScreenDialogue(
        onDismissRequest = onFinish,
        topAppBar = { },
    ) {
        UnableToConfirmIdentityModalNavHost(
            startDestination = startDestination,
            navGraphProviders = navGraphProviders,
            onFinish = onFinish,
        )
    }
}
