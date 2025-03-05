package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope
import javax.inject.Inject

@ContributesMultibinding(CriOrchestratorScope::class)
class ContinueToProveYourIdentityNavGraphProvider
    @Inject
    constructor() : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<ProveYourIdentityDestinations.ContinueToProveYourIdentity> {
                ContinueToProveYourIdentityScreen()
            }
        }
    }
