package uk.gov.onelogin.criorchestrator.features.dev.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

@Composable
@Deprecated("Use the variant accepting CriOrchestratorGraph. To be removed 15/11/2025.")
fun DevMenuScreen(
    component: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) = DevMenuScreen(
    graph = component as CriOrchestratorGraph,
    modifier = modifier,
)

@Composable
fun DevMenuScreen(
    graph: CriOrchestratorGraph,
    modifier: Modifier = Modifier,
) {
    (graph as DevMenuEntryPointsProviders).devMenuEntryPoints().DevMenuScreen(
        modifier = modifier,
    )
}
