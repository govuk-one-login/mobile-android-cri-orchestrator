package uk.gov.onelogin.criorchestrator.features.resume.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@Composable
@Deprecated(
    "Renamed component parameter to graph. To be removed 15/11/2025.",
)
fun ProveYourIdentityCard(
    component: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) = ProveYourIdentityCard(
    graph = component as CriOrchestratorGraph,
    modifier = modifier,
)

@Composable
fun ProveYourIdentityCard(
    graph: CriOrchestratorGraph,
    modifier: Modifier = Modifier,
) {
    (graph as ProveYourIdentityEntryPointsProviders).proveYourIdentityEntryPoints().ProveYourIdentityCard(
        modifier = modifier,
    )
}
