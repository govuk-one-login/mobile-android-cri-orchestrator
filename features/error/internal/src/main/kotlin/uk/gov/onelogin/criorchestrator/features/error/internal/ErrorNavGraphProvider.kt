package uk.gov.onelogin.criorchestrator.features.error.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror.RecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoSet(CriOrchestratorScope::class, binding = binding<AbortNavGraphProvider>())
@ContributesIntoSet(CriOrchestratorScope::class, binding = binding<ProveYourIdentityNavGraphProvider>())
class ErrorNavGraphProvider
    @Inject
    constructor(
        private val viewModelProviderFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider,
        AbortNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<ErrorDestinations.RecoverableError> {
                RecoverableErrorScreen(
                    navController = navController,
                    viewModel = viewModel(factory = viewModelProviderFactory),
                )
            }
        }
    }
