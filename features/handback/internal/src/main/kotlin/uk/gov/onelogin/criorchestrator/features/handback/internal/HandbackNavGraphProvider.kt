package uk.gov.onelogin.criorchestrator.features.handback.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortdesktopweb.ConfirmAbortDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortdesktopweb.ConfirmAbortToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortreturntodesktopweb.ConfirmAbortReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabortreturntodesktopweb.ConfirmAbortReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmaborttomobileweb.ConfirmAbortToMobileWeb
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class HandbackNavGraphProvider
    @Inject
    constructor(
        @Named(UnrecoverableErrorViewModelModule.FACTORY_NAME)
        private val unrecoverableErrorViewModelFactory: ViewModelProvider.Factory,
        @Named(ReturnToMobileWebViewModelModule.FACTORY_NAME)
        private val returnToMobileViewModelFactory: ViewModelProvider.Factory,
        @Named(ReturnToDesktopWebViewModelModule.FACTORY_NAME)
        private val returnToDesktopViewModelFactory: ViewModelProvider.Factory,
        @Named(ConfirmAbortDesktopWebViewModelModule.FACTORY_NAME)
        private val confirmAbortToDesktopWebViewModelFactory: ViewModelProvider.Factory,
        @Named(ConfirmAbortReturnToDesktopWebViewModelModule.FACTORY_NAME)
        private val confirmAbortReturnToDesktopWebViewModelFactory: ViewModelProvider.Factory,
        private val webNavigator: WebNavigator,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<HandbackDestinations.UnrecoverableError> {
                UnrecoverableErrorScreen(
                    navController = navController,
                    viewModel = viewModel(factory = unrecoverableErrorViewModelFactory),
                )
            }

            composable<HandbackDestinations.ReturnToMobileWeb> {
                ReturnToMobileWebScreen(
                    viewModel = viewModel(factory = returnToMobileViewModelFactory),
                    webNavigator = webNavigator,
                )
            }

            composable<HandbackDestinations.ReturnToDesktopWeb> {
                ReturnToDesktopWebScreen(
                    viewModel = viewModel(factory = returnToDesktopViewModelFactory),
                )
            }

            composable<HandbackDestinations.ConfirmAbortDesktopWeb> {
                ConfirmAbortToDesktopWebScreen(
                    viewModel = viewModel(factory = confirmAbortToDesktopWebViewModelFactory),
                    navController = navController,
                )
            }

            composable<HandbackDestinations.ConfirmAbortReturnDesktopWeb> {
                ConfirmAbortReturnToDesktopWebScreen(
                    viewModel = viewModel(factory = confirmAbortReturnToDesktopWebViewModelFactory),
                )
            }

            composable<HandbackDestinations.ConfirmAbortToMobileWeb> {
                ConfirmAbortToMobileWeb()
            }
        }
    }
