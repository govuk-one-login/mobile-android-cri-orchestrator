package uk.gov.onelogin.criorchestrator.features.handback.internal

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.ConfirmAbort
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class HandbackNavGraphProvider
    @Inject
    constructor(
        @Named(UnrecoverableErrorViewModelModule.FACTORY_NAME)
        private val viewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<HandbackDestinations.UnrecoverableError> {
                UnrecoverableErrorScreen(
                    navController = navController,
                    viewModel = viewModel(factory = viewModelFactory),
                )
            }

            composable<HandbackDestinations.ReturnToMobileWeb> {
                Text("Return to GOV.UK to finish proving your identity | DCMAW-11595")
            }

            composable<HandbackDestinations.ReturnToDesktopWeb> {
                Text("Return to GOV.UK to finish proving your identity | DCMAW-11596")
            }

            composable<HandbackDestinations.ConfirmAbort> {
                ConfirmAbort()
            }
        }
    }
