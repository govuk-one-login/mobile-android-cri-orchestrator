package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop.AbortedReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop.AbortedReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.AbortedRedirectToMobileWebHolderScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class AbortModalNavGraphProvider
    @Inject
    constructor(
        @Named(ConfirmAbortMobileViewModelModule.FACTORY_NAME)
        private val confirmAbortMobileWebViewModelFactory: ViewModelProvider.Factory,
        @Named(ConfirmAbortDesktopViewModelModule.FACTORY_NAME)
        private val confirmAbortDesktopWebViewModelFactory: ViewModelProvider.Factory,
        @Named(AbortedReturnToDesktopWebViewModelModule.FACTORY_NAME)
        private val abortedReturnToDesktopWebViewModelFactory: ViewModelProvider.Factory,
        @Named(UnrecoverableErrorViewModelModule.FACTORY_NAME)
        private val unrecoverableErrorViewModelFactory: ViewModelProvider.Factory,
        private val webNavigator: WebNavigator,
    ) : AbortNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<AbortDestinations.ConfirmAbortMobile> {
                ConfirmAbortMobileScreen(
                    viewModel = viewModel(factory = confirmAbortMobileWebViewModelFactory),
                    navController = navController,
                )
            }

            composable<AbortDestinations.ConfirmAbortDesktop> {
                ConfirmAbortDesktopWebScreen(
                    viewModel = viewModel(factory = confirmAbortDesktopWebViewModelFactory),
                    navController = navController,
                )
            }

            composable<AbortDestinations.AbortedReturnToDesktopWeb> {
                AbortedReturnToDesktopWebScreen(
                    viewModel = viewModel(factory = abortedReturnToDesktopWebViewModelFactory),
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
                    viewModel = viewModel(factory = unrecoverableErrorViewModelFactory),
                )
            }
        }
    }
