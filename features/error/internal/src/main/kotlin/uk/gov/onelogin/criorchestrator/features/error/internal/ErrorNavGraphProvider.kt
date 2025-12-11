package uk.gov.onelogin.criorchestrator.features.error.internal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.metroViewModel
import uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror.RecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoSet(CriOrchestratorScope::class, binding = binding<AbortNavGraphProvider>())
@ContributesIntoSet(CriOrchestratorScope::class, binding = binding<ProveYourIdentityNavGraphProvider>())
class ErrorNavGraphProvider :
    ProveYourIdentityNavGraphProvider,
    AbortNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<ErrorDestinations.RecoverableError> {
            RecoverableErrorScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }
    }
}
