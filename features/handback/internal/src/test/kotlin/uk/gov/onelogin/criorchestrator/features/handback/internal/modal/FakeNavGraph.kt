package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

/**
 * This test fixture uses string routes rather than type-safe
 * `@Serializable` destinations as the latter cannot live in test fixtures.
 *
 *  https://github.com/Kotlin/kotlinx.serialization/issues/2932
 */
object FakeNavGraph {
    class Provider : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<AbortDestinations.ConfirmAbortMobile> {}
        }
    }
}
