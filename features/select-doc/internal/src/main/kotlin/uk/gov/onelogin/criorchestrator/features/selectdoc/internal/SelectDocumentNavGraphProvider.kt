package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.select.brp.SelectBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.brp.ConfirmBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.drivinglicence.ConfirmDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.passport.ConfirmPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.select.drivinglicence.SelectDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.select.passport.SelectPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.select.passport.SelectPassportViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class SelectDocumentNavGraphProvider
    @Inject
    constructor(
        @Named(SelectPassportViewModelModule.FACTORY_NAME)
        private val viewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<SelectDocumentDestinations.Passport> {
                SelectPassportScreen(
                    navController = navController,
                    viewModel = viewModel(factory = viewModelFactory),
                )
            }

            composable<SelectDocumentDestinations.Brp> {
                SelectBrpScreen()
            }

            composable<SelectDocumentDestinations.DrivingLicence> {
                SelectDrivingLicenceScreen()
            }

            composable<SelectDocumentDestinations.TypesOfPhotoID> {
                TypesOfPhotoIDScreen()
            }

            composable<SelectDocumentDestinations.ConfirmPassport> {
                ConfirmPassportScreen()
            }

            composable<SelectDocumentDestinations.ConfirmBrp> {
                ConfirmBrpScreen()
            }

            composable<SelectDocumentDestinations.ConfirmDrivingLicence> {
                ConfirmDrivingLicenceScreen()
            }
        }
    }
