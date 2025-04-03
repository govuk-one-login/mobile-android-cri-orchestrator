package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select.SelectBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select.SelectDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation.ConfirmPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation.ConfirmPassportViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select.SelectPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select.SelectPassportViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select.SelectBrpViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirmation.ConfirmBrpScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select.SelectDrivingLicenceScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid.TypesOfPhotoIDViewModelModule
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class SelectDocNavGraphProvider
    @Inject
    constructor(
        @Named(SelectPassportViewModelModule.FACTORY_NAME)
        private val selectPassportViewModelFactory: ViewModelProvider.Factory,
        @Named(SelectBrpViewModelModule.FACTORY_NAME)
        private val selectBrpViewModelFactory: ViewModelProvider.Factory,
        @Named(ConfirmPassportViewModelModule.FACTORY_NAME)
        private val confirmPassportViewModelFactory: ViewModelProvider.Factory,
        @Named(TypesOfPhotoIDViewModelModule.FACTORY_NAME)
        private val typesOfPhotoIDViewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<SelectDocDestinations.Passport> {
                SelectPassportScreen(
                    navController = navController,
                    viewModel = viewModel(factory = selectPassportViewModelFactory),
                )
            }

            composable<SelectDocDestinations.Brp> {
                SelectBrpScreen(
                    navController = navController,
                    viewModel = viewModel(factory = selectBrpViewModelFactory),
                )
            }

            composable<SelectDocDestinations.DrivingLicence> {
                SelectDrivingLicenceScreen()
            }

            composable<SelectDocDestinations.TypesOfPhotoID> {
                TypesOfPhotoIDScreen(
                    viewModel = viewModel(factory = typesOfPhotoIDViewModelFactory),
                )
            }

            composable<SelectDocDestinations.ConfirmPassport> {
                ConfirmPassportScreen(
                    navController = navController,
                    viewModel = viewModel(factory = confirmPassportViewModelFactory),
                )
            }

            composable<SelectDocDestinations.ConfirmBrp> {
                ConfirmBrpScreen()
            }
        }
    }
