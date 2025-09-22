package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop.AbortedReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.AbortedRedirectToMobileWebHolderScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoSet(CriOrchestratorScope::class)
class AbortModalNavGraphProvider
    @Inject
    constructor(
        private val viewModelProviderFactory: ViewModelProvider.Factory,
        private val webNavigator: WebNavigator,
    ) : AbortNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<AbortDestinations.ConfirmAbortMobile> {
                ConfirmAbortMobileScreen(
                    viewModel = viewModel(factory = viewModelProviderFactory),
                    navController = navController,
                )
            }

            composable<AbortDestinations.ConfirmAbortDesktop> {
                ConfirmAbortDesktopWebScreen(
                    viewModel = viewModel(factory = viewModelProviderFactory),
                    navController = navController,
                )
            }

            composable<AbortDestinations.AbortedReturnToDesktopWeb> {
                AbortedReturnToDesktopWebScreen(
                    viewModel = viewModel(factory = viewModelProviderFactory),
                )
            }

            composable<AbortDestinations.AbortedRedirectToMobileWebHolder> { backStackEntry ->
                val redirectUri =
                    backStackEntry
                        .toRoute<AbortDestinations.AbortedRedirectToMobileWebHolder>()
                        .redirectUri
                AbortedRedirectToMobileWebHolderScreen(
                    webNavigator = webNavigator,
                    redirectUri = redirectUri,
                    onFinish = onFinish,
                )
            }

            composable<HandbackDestinations.UnrecoverableError> {
                UnrecoverableErrorScreen(
                    navController = navController,
                    viewModel = viewModel(factory = viewModelProviderFactory),
                )
            }
        }
    }
