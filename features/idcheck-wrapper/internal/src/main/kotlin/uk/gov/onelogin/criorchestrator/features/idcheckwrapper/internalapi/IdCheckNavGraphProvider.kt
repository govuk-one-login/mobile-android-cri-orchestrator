package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesMultibinding(CriOrchestratorScope::class)
class IdCheckNavGraphProvider
@Inject constructor() : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
        composable<IdCheckDestinations.SyncIdCheck> {
            val args = it.toRoute<IdCheckDestinations.SyncIdCheck>()
            SyncIdCheckScreen(
                args.idCheckSdkParameters,
            )
        }
    }
}

sealed interface IdCheckDestinations : ProveYourIdentityDestinations {
    @Parcelize
    data class SyncIdCheck(
        val idCheckSdkParameters: IdCheckSdkParameters
    ) : IdCheckDestinations
}