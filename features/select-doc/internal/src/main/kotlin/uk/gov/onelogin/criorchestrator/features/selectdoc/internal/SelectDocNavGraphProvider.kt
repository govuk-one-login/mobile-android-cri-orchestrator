package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.zacsweers.metro.ContributesIntoSet
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm.ConfirmBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select.SelectBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid.ConfirmNoChippedIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid.ConfirmNoNonChippedIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.confirm.ConfirmDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select.SelectDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirm.ConfirmPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select.SelectPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoSet(CriOrchestratorScope::class)
@Suppress("LongParameterList")
class SelectDocNavGraphProvider(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<SelectDocDestinations.Passport> {
            SelectPassportScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.Brp> {
            SelectBrpScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.DrivingLicence> {
            SelectDrivingLicenceScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.TypesOfPhotoID> {
            TypesOfPhotoIDScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.ConfirmPassport> {
            ConfirmPassportScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.ConfirmBrp> {
            ConfirmBrpScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.ConfirmDrivingLicence> {
            ConfirmDrivingLicenceScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.ConfirmNoChippedID> {
            ConfirmNoChippedIDScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<SelectDocDestinations.ConfirmNoNonChippedID> {
            ConfirmNoNonChippedIDScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }
    }
}
