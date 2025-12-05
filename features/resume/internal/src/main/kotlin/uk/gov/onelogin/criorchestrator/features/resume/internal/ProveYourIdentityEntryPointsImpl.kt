package uk.gov.onelogin.criorchestrator.features.resume.internal

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
import kotlinx.collections.immutable.toPersistentSet
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityRoot
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityEntryPoints
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityEntryPointsProviders
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@SingleIn(CriOrchestratorScope::class)
@ContributesBinding(CriOrchestratorScope::class)
class ProveYourIdentityEntryPointsImpl(
    private val metroVmf: MetroViewModelFactory,
    navGraphProviders: Set<ProveYourIdentityNavGraphProvider>,
) : ProveYourIdentityEntryPoints {
    private val navGraphProviders = navGraphProviders.toPersistentSet()

    @Composable
    override fun ProveYourIdentityCard(modifier: Modifier) {
        CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
            ProveYourIdentityRoot(
                viewModel = metroViewModel(),
                navGraphProviders = navGraphProviders,
                modifier = modifier.testTag(TEST_TAG),
            )
        }
    }

    companion object {
        internal val TEST_TAG = this::class.java.simpleName
    }
}

@SingleIn(CriOrchestratorScope::class)
@ContributesTo(CriOrchestratorScope::class)
interface ContributedEntryPointsProviders : ProveYourIdentityEntryPointsProviders
