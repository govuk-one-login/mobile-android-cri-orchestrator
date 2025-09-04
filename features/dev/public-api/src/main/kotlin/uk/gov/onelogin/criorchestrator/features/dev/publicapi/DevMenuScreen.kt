package uk.gov.onelogin.criorchestrator.features.dev.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.features.dev.internalapi.DevMenuEntryPointsProviders
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@Composable
@Deprecated("Use the variant accepting CriOrchestratorGraph")
fun DevMenuScreen(
    component: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) {
    (component as DevMenuEntryPointsProviders).devMenuEntryPoints().DevMenuScreen(
        modifier = modifier,
    )
}

@Composable
fun DevMenuScreen(
    graph: CriOrchestratorGraph,
    modifier: Modifier = Modifier,
) {
    (graph as DevMenuEntryPointsProviders).devMenuEntryPoints().DevMenuScreen(
        modifier = modifier,
    )
}
