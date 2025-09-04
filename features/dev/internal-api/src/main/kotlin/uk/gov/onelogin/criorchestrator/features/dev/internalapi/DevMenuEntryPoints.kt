package uk.gov.onelogin.criorchestrator.features.dev.internalapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.SingleIn
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Immutable
fun interface DevMenuEntryPoints {
    @Composable
    fun DevMenuScreen(modifier: Modifier)
}

@SingleIn(CriOrchestratorScope::class)
@ContributesTo(CriOrchestratorScope::class)
fun interface DevMenuEntryPointsProviders {
    fun devMenuEntryPoints(): DevMenuEntryPoints
}
