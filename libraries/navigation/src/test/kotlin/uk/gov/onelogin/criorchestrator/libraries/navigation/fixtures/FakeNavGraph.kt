package uk.gov.onelogin.criorchestrator.libraries.navigation.fixtures

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

/**
 * This test fixture lives in the test source set because
 * `@Serializable` destinations cannot live in test fixtures.
 *
 *  https://github.com/Kotlin/kotlinx.serialization/issues/2932
 */
object FakeNavGraph {
    sealed interface Destination : NavigationDestination {
        @Serializable
        data object First : Destination

        @Serializable
        data object Second : Destination
    }

    object Buttons {
        const val FIRST = "Go to second screen"
        const val SECOND = "Back to first screen"
    }

    class Provider : NavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<Destination.First> {
                Button(
                    onClick = {
                        navController.navigate(Destination.Second)
                    },
                ) {
                    Text(
                        text = Buttons.FIRST,
                    )
                }
            }
            composable<Destination.Second> {
                Button(
                    onClick = {
                        navController.navigate(Destination.First)
                    },
                ) {
                    Text(
                        text = Buttons.SECOND,
                    )
                }
            }
        }
    }
}
