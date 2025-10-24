package uk.gov.onelogin.criorchestrator.features.handback.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import dev.zacsweers.metro.ContributesIntoSet
import kotlinx.collections.immutable.toPersistentSet
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop.FaceScanLimitReachedDesktopScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile.FaceScanLimitReachedMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModal
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.composeutils.fullScreenDialogProperties
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Suppress("LongMethod", "LongParameterList")
@ContributesIntoSet(CriOrchestratorScope::class)
class HandbackNavGraphProvider(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val webNavigator: WebNavigator,
    private val abortNavGraphProviders: Set<AbortNavGraphProvider>,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<HandbackDestinations.UnrecoverableError> {
            UnrecoverableErrorScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<HandbackDestinations.ReturnToMobileWeb> { backStackEntry ->
            val redirectUri =
                backStackEntry
                    .toRoute<HandbackDestinations.ReturnToMobileWeb>()
                    .redirectUri
            ReturnToMobileWebScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
                webNavigator = webNavigator,
                redirectUri = redirectUri,
            )
        }

        composable<HandbackDestinations.ReturnToDesktopWeb> {
            ReturnToDesktopWebScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        composable<HandbackDestinations.FaceScanLimitReachedMobile> { backStackEntry ->
            val redirectUri =
                backStackEntry
                    .toRoute<HandbackDestinations.FaceScanLimitReachedMobile>()
                    .redirectUri
            FaceScanLimitReachedMobileScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
                webNavigator = webNavigator,
                redirectUri = redirectUri,
            )
        }

        composable<HandbackDestinations.FaceScanLimitReachedDesktop> {
            FaceScanLimitReachedDesktopScreen(
                viewModel = viewModel(factory = viewModelProviderFactory),
            )
        }

        dialog<AbortDestinations.ConfirmAbortDesktop>(
            dialogProperties = fullScreenDialogProperties,
        ) {
            AbortModal(
                abortModalViewModel = viewModel(factory = viewModelProviderFactory),
                startDestination = AbortDestinations.ConfirmAbortDesktop,
                navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                onDismissRequest = { navController.popBackStack() },
                onFinish = onFinish,
            )
        }

        dialog<AbortDestinations.ConfirmAbortMobile>(
            dialogProperties = fullScreenDialogProperties,
        ) {
            AbortModal(
                abortModalViewModel = viewModel(factory = viewModelProviderFactory),
                startDestination = AbortDestinations.ConfirmAbortMobile,
                navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                onDismissRequest = { navController.popBackStack() },
                onFinish = onFinish,
            )
        }

        dialog<AbortDestinations.AbortedReturnToDesktopWeb>(
            dialogProperties = fullScreenDialogProperties,
        ) {
            AbortModal(
                abortModalViewModel = viewModel(factory = viewModelProviderFactory),
                startDestination = AbortDestinations.AbortedReturnToDesktopWeb,
                navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                onDismissRequest = { navController.popBackStack() },
                onFinish = onFinish,
            )
        }

        dialog<AbortDestinations.AbortedRedirectToMobileWebHolder>(
            dialogProperties = fullScreenDialogProperties,
        ) { backStackEntry ->
            val redirectUri =
                backStackEntry
                    .toRoute<AbortDestinations.AbortedRedirectToMobileWebHolder>()
                    .redirectUri
            AbortModal(
                abortModalViewModel = viewModel(factory = viewModelProviderFactory),
                startDestination = AbortDestinations.AbortedRedirectToMobileWebHolder(redirectUri),
                navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                onDismissRequest = { navController.popBackStack() },
                onFinish = onFinish,
            )
        }
    }
}
