package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortdesktopweb.ConfirmAbortDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktopweb.ConfirmAbortReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmaborttomobileweb.ConfirmAbortToMobileWeb
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmaborttomobileweb.ConfirmAbortToMobileWebViewModel

@Composable
fun AbortModalNavHost(
    startDestination: String,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<AbortDestinations.ConfirmAbortToMobileWeb> {
            AbortMobile(
                onClose = { navController.popBackStack() }
            )
        }

        composable<AbortDestinations.ConfirmAbortDesktopWeb> {
            AbortDesktop(
                onNext = {
                    navController.navigate(AbortDestinations.ConfirmAbortReturnDesktopWeb)
                },
                onClose = { navController.popBackStack() }
            )
        }

        composable<AbortDestinations.ConfirmAbortReturnDesktopWeb> {
            AbortReturnDesktop(
                onClose = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun AbortMobile(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(spacingDouble)) {
        Text("Abort mobile")

        Button(onClose) {
            Text("Close")
        }
    }
}

@Composable
fun AbortDesktop(
    onClose: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(spacingDouble)) {
        Text("Abort desktop")

        Button(onNext) {
            Text("Next")
        }

        Button(onClose) {
            Text("Close")
        }
    }
}

@Composable
fun AbortReturnDesktop(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(spacingDouble)) {
        Text("Abort return desktop")

        Button(onClose) {
            Text("Close")
        }
    }
}