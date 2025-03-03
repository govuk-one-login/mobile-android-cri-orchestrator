package uk.gov.onelogin.criorchestrator.features.dev.internalapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope

@Immutable
fun interface DevMenuEntryPoints {
    @Composable
    fun DevMenuScreen(modifier: Modifier)
}

@ActivityScope
@ContributesTo(CriOrchestratorScope::class)
fun interface DevMenuEntryPointsComponent {
    fun devMenuEntryPoints(): DevMenuEntryPoints
}
