package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider

object FakeAbortModalNavGraph {
    class Provider : AbortNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<AbortDestinations.ConfirmAbortMobile> {}
        }
    }
}
