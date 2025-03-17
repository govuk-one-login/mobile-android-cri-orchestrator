package uk.gov.onelogin.criorchestrator.features.dev.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuScreen
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuViewModelModule
import uk.gov.onelogin.criorchestrator.features.dev.internalapi.DevMenuEntryPoints
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class DevMenuEntryPointsImpl
    @Inject
    constructor(
        @Named(DevMenuViewModelModule.FACTORY_NAME)
        private val viewModelProviderFactory: ViewModelProvider.Factory,
    ) : DevMenuEntryPoints {
        @Composable
        override fun DevMenuScreen(modifier: Modifier) {
            DevMenuScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
                modifier = modifier.testTag(TEST_TAG),
            )
        }

        companion object {
            internal val TEST_TAG = this.javaClass.simpleName
        }
    }
