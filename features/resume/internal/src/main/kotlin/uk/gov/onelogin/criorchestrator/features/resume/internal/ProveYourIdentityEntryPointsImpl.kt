package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.collections.immutable.toPersistentSet
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityRoot
import uk.gov.onelogin.criorchestrator.features.resume.internal.root.ProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.ProveYourIdentityEntryPoints
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class ProveYourIdentityEntryPointsImpl
    @Inject
    constructor(
        @Named(ProveYourIdentityViewModelModule.FACTORY_NAME)
        private val viewModelProviderFactory: ViewModelProvider.Factory,
        navGraphProviders: Set<@JvmSuppressWildcards ProveYourIdentityNavGraphProvider>,
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
            internal val TEST_TAG = this.javaClass.simpleName
        }
    }
