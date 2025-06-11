package uk.gov.onelogin.criorchestrator.features.resume.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.ProveYourIdentityEntryPointsComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

@Composable
fun ProveYourIdentityCard(
    component: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    (component as ProveYourIdentityEntryPointsComponent).proveYourIdentityEntryPoints().ProveYourIdentityCard(
        modifier = modifier,
        navController = navController,
    )
}
