package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.ContinueToProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class ContinueToProveYourIdentityNavGraphProvider
    @Inject
    constructor(
        @Named(ContinueToProveYourIdentityViewModelModule.FACTORY_NAME)
        private val viewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<ProveYourIdentityDestinations.ContinueToProveYourIdentity> {
                ContinueToProveYourIdentityScreen(
                    viewModel = viewModel(factory = viewModelFactory),
                    navController = navController,
                )
            }

            composable<ContinueToProveYourIdentityDestinations.PassportJourney> {
            }

            composable<ContinueToProveYourIdentityDestinations.DrivingLicenceJourney> {
            }
        }
    }
