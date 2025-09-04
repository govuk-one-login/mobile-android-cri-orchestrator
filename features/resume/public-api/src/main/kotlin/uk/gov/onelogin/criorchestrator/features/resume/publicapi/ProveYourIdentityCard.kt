package uk.gov.onelogin.criorchestrator.features.resume.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.ProveYourIdentityEntryPointsProviders
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@Composable
@Deprecated(
    "Renamed component parameter to graph",
)
fun ProveYourIdentityCard(
    component: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) {
    (component as ProveYourIdentityEntryPointsProviders).proveYourIdentityEntryPoints().ProveYourIdentityCard(
        modifier = modifier,
    )
}

@Composable
fun ProveYourIdentityCard(
    graph: CriOrchestratorGraph,
    modifier: Modifier = Modifier,
) {
    (graph as ProveYourIdentityEntryPointsProviders).proveYourIdentityEntryPoints().ProveYourIdentityCard(
        modifier = modifier,
    )
}
