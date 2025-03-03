package uk.gov.onelogin.criorchestrator.features.dev.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.features.dev.internalapi.DevMenuEntryPointsComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

@Composable
fun DevMenuScreen(
    component: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) {
    (component as DevMenuEntryPointsComponent).devMenuEntryPoints().DevMenuScreen(
        modifier = modifier,
    )
}
