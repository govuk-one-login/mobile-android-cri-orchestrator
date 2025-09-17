package uk.gov.onelogin.criorchestrator.features.dev.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Immutable
fun interface DevMenuEntryPoints {
    @Composable
    fun DevMenuScreen(modifier: Modifier)
}

fun interface DevMenuEntryPointsProviders {
    fun devMenuEntryPoints(): DevMenuEntryPoints
}
