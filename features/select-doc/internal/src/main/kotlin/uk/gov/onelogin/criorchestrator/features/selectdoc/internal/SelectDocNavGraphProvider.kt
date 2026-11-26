package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metrox.viewmodel.metroViewModel
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
class SelectDocNavGraphProvider : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(navController: NavController, onFinish: () -> Unit) {
        composable<SelectDocDestinations.Passport> {
            SelectPassportScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        composable<SelectDocDestinations.Brp> {
            SelectBrpScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        composable<SelectDocDestinations.DrivingLicence> {
            SelectDrivingLicenceScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        dialog<SelectDocDestinations.TypesOfPhotoID>(
            dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            TypesOfPhotoIDScreen(
                viewModel = metroViewModel(),
            ) {
                navController.popBackStack()
            }
        }

        composable<SelectDocDestinations.ConfirmPassport> {
            ConfirmPassportScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        composable<SelectDocDestinations.ConfirmBrp> {
            ConfirmBrpScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        composable<SelectDocDestinations.ConfirmDrivingLicence> {
            ConfirmDrivingLicenceScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        composable<SelectDocDestinations.ConfirmNoChippedID> {
            ConfirmNoChippedIDScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }

        composable<SelectDocDestinations.ConfirmNoNonChippedID> {
            ConfirmNoNonChippedIDScreen(
                navController = navController,
                viewModel = metroViewModel(),
            )
        }
    }
}
