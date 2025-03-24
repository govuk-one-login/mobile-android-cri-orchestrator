package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.SelectBRPScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.ConfirmDocumentScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.SelectPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.SelectPassportViewModelModule
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

            composable<SelectDocumentDestinations.BRP> {
                SelectBRPScreen()
            }

            composable<SelectDocumentDestinations.DrivingLicence> {
                @Composable
                fun Test() {
                    Text("Driving License check")
                }
            }

            composable<SelectDocumentDestinations.TypesOfPhotoID> {
                TypesOfPhotoIDScreen()
            }

            composable<SelectDocumentDestinations.Confirm> {
                ConfirmDocumentScreen()
            }
        }
    }
