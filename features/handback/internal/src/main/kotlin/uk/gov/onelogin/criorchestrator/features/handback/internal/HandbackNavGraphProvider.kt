package uk.gov.onelogin.criorchestrator.features.handback.internal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.collections.immutable.toPersistentSet
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile.FaceScanLimitReachedMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile.FaceScanLimitReachedMobileViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModal
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModalViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb.ReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb.ReturnToMobileWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror.UnrecoverableErrorViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.composeutils.fullScreenDialogProperties
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@Suppress("LongMethod", "LongParameterList")
@ContributesMultibinding(CriOrchestratorScope::class)
class HandbackNavGraphProvider
    @Inject
    constructor(
        @Named(AbortModalViewModelModule.FACTORY_NAME)
        private val abortModalViewModelFactory: ViewModelProvider.Factory,
        @Named(UnrecoverableErrorViewModelModule.FACTORY_NAME)
        private val unrecoverableErrorViewModelFactory: ViewModelProvider.Factory,
        @Named(ReturnToMobileWebViewModelModule.FACTORY_NAME)
        private val returnToMobileViewModelFactory: ViewModelProvider.Factory,
        @Named(ReturnToDesktopWebViewModelModule.FACTORY_NAME)
        private val returnToDesktopViewModelFactory: ViewModelProvider.Factory,
        @Named(FaceScanLimitReachedMobileViewModelModule.FACTORY_NAME)
        private val faceScanLimitReachedViewModelFactory: ViewModelProvider.Factory,
        private val webNavigator: WebNavigator,
        private val abortNavGraphProviders: Set<@JvmSuppressWildcards AbortNavGraphProvider>,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<HandbackDestinations.UnrecoverableError> {
                UnrecoverableErrorScreen(
                    navController = navController,
                    viewModel = viewModel(factory = unrecoverableErrorViewModelFactory),
                )
            }

            composable<HandbackDestinations.ReturnToMobileWeb> { backStackEntry ->
                val redirectUri =
                    backStackEntry
                        .toRoute<HandbackDestinations.ReturnToMobileWeb>()
                        .redirectUri
                ReturnToMobileWebScreen(
                    viewModel = viewModel(factory = returnToMobileViewModelFactory),
                    webNavigator = webNavigator,
                    redirectUri = redirectUri,
                )
            }

            composable<HandbackDestinations.ReturnToDesktopWeb> {
                ReturnToDesktopWebScreen(
                    viewModel = viewModel(factory = returnToDesktopViewModelFactory),
                )
            }

            composable<HandbackDestinations.FaceScanLimitReachedMobile> { backStackEntry ->
                val redirectUri =
                    backStackEntry
                        .toRoute<HandbackDestinations.FaceScanLimitReachedMobile>()
                        .redirectUri
                FaceScanLimitReachedMobileScreen(
                    viewModel = viewModel(factory = faceScanLimitReachedViewModelFactory),
                    webNavigator = webNavigator,
                    redirectUri = redirectUri,
                )
            }

            dialog<AbortDestinations.ConfirmAbortDesktop>(
                dialogProperties = fullScreenDialogProperties,
            ) {
                AbortModal(
                    abortModalViewModel = viewModel(factory = abortModalViewModelFactory),
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
                    abortModalViewModel = viewModel(factory = abortModalViewModelFactory),
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
                    abortModalViewModel = viewModel(factory = abortModalViewModelFactory),
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
                    abortModalViewModel = viewModel(factory = abortModalViewModelFactory),
                    startDestination = AbortDestinations.AbortedRedirectToMobileWebHolder(redirectUri),
                    navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                    onDismissRequest = { navController.popBackStack() },
                    onFinish = onFinish,
                )
            }
        }
    }
