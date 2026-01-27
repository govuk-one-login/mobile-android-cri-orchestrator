package uk.gov.onelogin.criorchestrator.features.resume.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@Composable
fun ProveYourIdentityCard(
    graph: CriOrchestratorGraph,
    modifier: Modifier = Modifier,
) {
    (graph as ProveYourIdentityEntryPointsProviders).proveYourIdentityEntryPoints().ProveYourIdentityCard(
        modifier = modifier,
    )
}
