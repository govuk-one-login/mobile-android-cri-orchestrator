package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.SelectBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.SelectBrpViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.ConfirmDocumentScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmbrp.BrpConfirmationScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.SelectDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.SelectPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.SelectPassportViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class SelectDocumentNavGraphProvider
@Inject
constructor(
    @Named(SelectBrpViewModelModule.FACTORY_NAME)
    private val selectBrpViewModelFactory: ViewModelProvider.Factory,
    @Named(SelectPassportViewModelModule.FACTORY_NAME)
    private val selectPassportViewModelFactory: ViewModelProvider.Factory,
    @Named(TypesOfPhotoIDViewModelModule.FACTORY_NAME)
    private val typesOfPhotoIDViewModelFactory: ViewModelProvider.Factory,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
        composable<SelectDocumentDestinations.Passport> {
            SelectPassportScreen(
                navController = navController,
                viewModel = viewModel(factory = selectPassportViewModelFactory),
            )
        }
        composable<SelectDocumentDestinations.Brp> {
            SelectBrpScreen(
                navController = navController,
                viewModel = viewModel(factory = selectBrpViewModelFactory)
            )
        }
        
        composable<SelectDocumentDestinations.Brp> {
            SelectBrpScreen(
                navController = navController,
                viewModel = viewModel(factory = selectBrpViewModelFactory),
            )
        }

        composable<SelectDocumentDestinations.DrivingLicence> {
            SelectDrivingLicenceScreen()
        }

        composable<SelectDocumentDestinations.TypesOfPhotoID> {
            TypesOfPhotoIDScreen(
                viewModel = viewModel(factory = typesOfPhotoIDViewModelFactory),
            )
        }

        composable<SelectDocumentDestinations.Confirm> {
            ConfirmDocumentScreen()
        }

        composable<SelectDocumentDestinations.BrpConfirmation> {
            BrpConfirmationScreen()
        }
    }
}
