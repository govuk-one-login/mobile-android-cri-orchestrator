package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.collections.immutable.toPersistentSet
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModal
import uk.gov.onelogin.criorchestrator.features.handback.internal.modal.AbortModalViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.desktop.UnableToConfirmIdentityDesktopScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.desktop.UnableToConfirmIdentityDesktopViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile.UnableToConfirmIdentityMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile.UnableToConfirmIdentityMobileViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class UnableToConfirmIdentityModalNavGraphProvider
    @Inject
    constructor(
        @Named(AbortModalViewModelModule.FACTORY_NAME)
        private val abortModalViewModelFactory: ViewModelProvider.Factory,
        @Named(UnableToConfirmIdentityDesktopViewModelModule.FACTORY_NAME)
        private val unableToConfirmIdentityDesktopViewModelFactory: ViewModelProvider.Factory,
        @Named(UnableToConfirmIdentityMobileViewModelModule.FACTORY_NAME)
        private val unableToConfirmIdentityMobileViewModelFactory: ViewModelProvider.Factory,
        private val abortNavGraphProviders: Set<@JvmSuppressWildcards AbortNavGraphProvider>,
    ) : UnableToConfirmIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<UnableToConfirmIdentityDestinations.UnableToConfirmIdentityDesktop> {
                UnableToConfirmIdentityDesktopScreen(
                    viewModel = viewModel(factory = unableToConfirmIdentityDesktopViewModelFactory),
                    navController = navController,
                )
            }

            composable<UnableToConfirmIdentityDestinations.UnableToConfirmIdentityMobile> {
                UnableToConfirmIdentityMobileScreen(
                    viewModel = viewModel(factory = unableToConfirmIdentityMobileViewModelFactory),
                    navController = navController,
                )
            }

            composable<AbortDestinations.AbortedReturnToDesktopWeb> {
                AbortModal(
                    abortModalViewModel = viewModel(factory = abortModalViewModelFactory),
                    startDestination = AbortDestinations.AbortedReturnToDesktopWeb,
                    navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                    onDismissRequest = { navController.popBackStack() },
                    onFinish = onFinish,
                )
            }

            composable<AbortDestinations.AbortedRedirectToMobileWebHolder> { backStackEntry ->
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

            composable<HandbackDestinations.UnrecoverableError> {
                AbortModal(
                    abortModalViewModel = viewModel(factory = abortModalViewModelFactory),
                    startDestination = HandbackDestinations.UnrecoverableError,
                    navGraphProviders = abortNavGraphProviders.toPersistentSet(),
                    onDismissRequest = { navController.popBackStack() },
                    onFinish = onFinish,
                )
            }
        }
    }
