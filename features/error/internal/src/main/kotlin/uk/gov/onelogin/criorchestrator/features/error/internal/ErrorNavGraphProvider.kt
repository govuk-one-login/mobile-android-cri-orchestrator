package uk.gov.onelogin.criorchestrator.features.error.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror.RecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror.RecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class, boundType = AbortNavGraphProvider::class)
@ContributesMultibinding(CriOrchestratorScope::class, boundType = ProveYourIdentityNavGraphProvider::class)
@ContributesMultibinding(CriOrchestratorScope::class, boundType = UnableToConfirmIdentityNavGraphProvider::class)
class ErrorNavGraphProvider
    @Inject
    constructor(
        @Named(RecoverableErrorViewModelModule.FACTORY_NAME)
        private val recoverableErrorViewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider,
        AbortNavGraphProvider,
        UnableToConfirmIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<ErrorDestinations.RecoverableError> {
                RecoverableErrorScreen(
                    navController = navController,
                    viewModel = viewModel(factory = recoverableErrorViewModelFactory),
                )
            }
        }
    }
