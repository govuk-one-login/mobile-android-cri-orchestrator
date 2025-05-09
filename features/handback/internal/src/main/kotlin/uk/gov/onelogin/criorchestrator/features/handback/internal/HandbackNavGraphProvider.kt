package uk.gov.onelogin.criorchestrator.features.handback.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.collections.immutable.persistentSetOf
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModal
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@Suppress("LongParameterList")
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
        private val webNavigator: WebNavigator,
        private val abortModalNavGraphProvider: AbortNavGraphProvider,
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
                AbortModal(
                    startDestination = AbortDestinations.ConfirmAbortDesktop,
                    navGraphProviders = persistentSetOf(abortModalNavGraphProvider),
                    onDismiss = { navController.popBackStack() },
                )
            }

            composable<HandbackDestinations.ConfirmAbortToMobileWeb> {
                AbortModal(
                    startDestination = AbortDestinations.ConfirmAbortMobile,
                    navGraphProviders = persistentSetOf(abortModalNavGraphProvider),
                    onDismiss = { navController.popBackStack() },
                )
            }

            // This should be removed with DCMAW-13211
            composable<HandbackDestinations.ConfirmAbort> {
                AbortModal(
                    startDestination = AbortDestinations.ConfirmAbortDesktop,
                    navGraphProviders = persistentSetOf(abortModalNavGraphProvider),
                    onDismiss = { navController.popBackStack() },
                )
            }
        }
    }
