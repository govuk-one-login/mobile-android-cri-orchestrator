package uk.gov.onelogin.criorchestrator.features.dev.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@Composable
fun DevMenuScreen(
    graph: CriOrchestratorGraph,
    modifier: Modifier = Modifier,
) {
    (graph as DevMenuEntryPointsProviders).devMenuEntryPoints().DevMenuScreen(
        modifier = modifier,
    )
}
