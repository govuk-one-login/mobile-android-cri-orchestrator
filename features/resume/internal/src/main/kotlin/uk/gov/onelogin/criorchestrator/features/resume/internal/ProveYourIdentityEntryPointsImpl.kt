package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.SingleIn
import kotlinx.collections.immutable.toPersistentSet
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityRoot
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.ProveYourIdentityEntryPoints
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@SingleIn(CriOrchestratorScope::class)
@ContributesBinding(CriOrchestratorScope::class)
class ProveYourIdentityEntryPointsImpl
    @Inject
    constructor(
        @Named(ProveYourIdentityViewModelModule.FACTORY_NAME)
        private val viewModelProviderFactory: ViewModelProvider.Factory,
        navGraphProviders: Set<ProveYourIdentityNavGraphProvider>,
    ) : ProveYourIdentityEntryPoints {
        private val navGraphProviders = navGraphProviders.toPersistentSet()

        @Composable
        override fun ProveYourIdentityCard(modifier: Modifier) {
            ProveYourIdentityRoot(
                viewModel = viewModel(factory = viewModelProviderFactory),
                navGraphProviders = navGraphProviders,
                modifier = modifier.testTag(TEST_TAG),
            )
        }

        companion object {
            internal val TEST_TAG = this::class.java.simpleName
        }
    }
