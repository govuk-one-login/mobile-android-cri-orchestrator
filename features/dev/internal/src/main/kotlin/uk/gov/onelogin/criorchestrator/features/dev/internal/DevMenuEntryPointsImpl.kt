package uk.gov.onelogin.criorchestrator.features.dev.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.metroViewModel
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuScreen
import uk.gov.onelogin.criorchestrator.features.dev.publicapi.DevMenuEntryPoints
import uk.gov.onelogin.criorchestrator.features.dev.publicapi.DevMenuEntryPointsProviders
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@SingleIn(CriOrchestratorScope::class)
@ContributesBinding(CriOrchestratorScope::class)
class DevMenuEntryPointsImpl(
    private val metroVmf: MetroViewModelFactory,
) : DevMenuEntryPoints {
    @Composable
    override fun DevMenuScreen(modifier: Modifier) {
        CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
            DevMenuScreen(
                viewModel = metroViewModel(),
                modifier = modifier.testTag(TEST_TAG),
            )
        }
    }

    companion object {
        internal val TEST_TAG = this.javaClass.simpleName
    }
}

@SingleIn(CriOrchestratorScope::class)
@ContributesTo(CriOrchestratorScope::class)
interface ContributedEntryPointsProviders : DevMenuEntryPointsProviders
