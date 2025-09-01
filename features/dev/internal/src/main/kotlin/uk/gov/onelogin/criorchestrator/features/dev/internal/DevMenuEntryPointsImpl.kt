package uk.gov.onelogin.criorchestrator.features.dev.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.SingleIn
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuScreen
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.DevMenuViewModelModule
import uk.gov.onelogin.criorchestrator.features.dev.internalapi.DevMenuEntryPoints
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@SingleIn(CriOrchestratorScope::class)
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
