package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityNavGraphProvider

object FakeUnableToConfirmIdentityModalNavGraph {
    class Provider : UnableToConfirmIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<UnableToConfirmIdentityDestinations.UnableToConfirmIdentityMobile> {
                Text("Test Text")
            }
        }
    }
}
