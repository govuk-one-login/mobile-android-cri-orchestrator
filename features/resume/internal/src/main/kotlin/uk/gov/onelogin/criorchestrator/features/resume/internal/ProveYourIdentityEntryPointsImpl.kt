package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.ProveYourIdentityEntryPoints
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class ProveYourIdentityEntryPointsImpl
    @Inject
    constructor(
        @Named(ProveYourIdentityViewModelModule.FACTORY_NAME)
        private val viewModelProviderFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityEntryPoints {
        @Composable
        override fun ProveYourIdentityCard(modifier: Modifier) {
            ProveYourIdentityRoot(
                viewModel = viewModel(factory = viewModelProviderFactory),
                modifier = modifier,
            )
        }
    }
