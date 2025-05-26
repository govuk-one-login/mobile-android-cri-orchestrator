package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.CompositeNavHost

@Composable
fun UnableToConfirmIdentityModalNavHost(
    navGraphProviders: ImmutableSet<UnableToConfirmIdentityNavGraphProvider>,
    startDestination: UnableToConfirmIdentityDestinations,
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {},
) = CompositeNavHost(
    startDestination = startDestination,
    navGraphProviders = navGraphProviders,
    onFinish = onFinish,
    modifier = modifier,
)
