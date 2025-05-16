package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableSet
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.CompositeNavHost

@Composable
fun AbortModalNavHost(
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    startDestination: AbortDestinations,
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {},
) = CompositeNavHost(
    startDestination = startDestination,
    navGraphProviders = navGraphProviders,
    onFinish = onFinish,
    modifier = modifier,
)
