package uk.gov.onelogin.criorchestrator.libraries.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TestNavHost(
    originText: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = "origin"

    NavHost(
        navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable("origin") {
            Button(
                onClick = {
                    navController.navigate("destination")
                },
            ) {
                Text(originText)
            }
        }

        composable("destination") {
            content()
        }
    }
}
