package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.zacsweers.metro.ContributesIntoSet
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoSet(CriOrchestratorScope::class)
class ContinueToProveYourIdentityNavGraphProvider(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<ProveYourIdentityDestinations.ContinueToProveYourIdentity> {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
                navController = navController,
            )
        }
    }
}
