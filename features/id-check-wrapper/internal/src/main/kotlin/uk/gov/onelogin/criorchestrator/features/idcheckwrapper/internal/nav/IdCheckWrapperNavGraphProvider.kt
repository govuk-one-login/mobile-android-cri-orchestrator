package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.zacsweers.metro.ContributesIntoSet
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckScreen
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nav.IdCheckWrapperDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoSet(CriOrchestratorScope::class)
class IdCheckWrapperNavGraphProvider(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<IdCheckWrapperDestinations.SyncIdCheckScreen> {
            val args = it.toRoute<IdCheckWrapperDestinations.SyncIdCheckScreen>()
            SyncIdCheckScreen(
                documentVariety = args.documentVariety,
                viewModel = viewModel(factory = viewModelProviderFactory),
                navController = navController,
            )
        }
    }
}
