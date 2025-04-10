package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckScreen
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckViewModelModule
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nav.IdCheckWrapperDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class IdCheckWrapperNavGraphProvider
    @Inject
    constructor(
        @Named(SyncIdCheckViewModelModule.FACTORY_NAME)
        private val syncIdCheckViewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<IdCheckWrapperDestinations.SyncIdCheckScreen> {
                val args = it.toRoute<IdCheckWrapperDestinations.SyncIdCheckScreen>()
                SyncIdCheckScreen(
                    documentVariety = args.documentVariety,
                    viewModel = viewModel(factory = syncIdCheckViewModelFactory),
                    navController = navController,
                )
            }
        }
    }
