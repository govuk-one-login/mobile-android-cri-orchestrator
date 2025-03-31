package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.IdCheckDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.SyncIdCheckScreen
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import kotlin.reflect.typeOf

@ContributesMultibinding(CriOrchestratorScope::class)
class IdCheckNavGraphProvider
@Inject constructor() : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
        composable<IdCheckDestinations.SyncIdCheck>(
            typeMap = mapOf(typeOf<IdCheckSdkParameters>() to IdCheckSdkParameterType)
        ) {
            val args = it.toRoute<IdCheckDestinations.SyncIdCheck>()
            SyncIdCheckScreen(
                args.idCheckSdkParameters,
            )
        }
    }
}
