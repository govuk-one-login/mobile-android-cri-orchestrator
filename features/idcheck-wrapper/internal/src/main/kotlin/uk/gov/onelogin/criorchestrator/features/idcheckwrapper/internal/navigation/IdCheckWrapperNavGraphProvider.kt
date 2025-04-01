package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.IdCheckWrapperDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.SyncIdCheckScreen
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.SyncSdkViewModelModule
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named
import kotlin.reflect.typeOf

@ContributesMultibinding(CriOrchestratorScope::class)
class IdCheckWrapperNavGraphProvider
    @Inject
    constructor(
        @Named(SyncSdkViewModelModule.FACTORY_NAME)
        private val syncSdkViewModelFactory: ViewModelProvider.Factory,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<IdCheckWrapperDestinations.SyncIdCheckScreen>(
                typeMap = mapOf(typeOf<DocumentType>() to DocumentTypeNavType),
            ) {
                val args = it.toRoute<IdCheckWrapperDestinations.SyncIdCheckScreen>()
                SyncIdCheckScreen(
                    documentType = args.documentType,
                    viewModel = viewModel(factory = syncSdkViewModelFactory),
                )
            }
        }
    }
