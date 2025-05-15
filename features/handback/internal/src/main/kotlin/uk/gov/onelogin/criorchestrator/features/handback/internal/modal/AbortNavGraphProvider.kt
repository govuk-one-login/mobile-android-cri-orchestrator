package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop.AbortedReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop.AbortedReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop.ConfirmAbortDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile.ConfirmAbortMobileViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class AbortNavGraphProvider
    @Inject
    constructor(
        @Named(ConfirmAbortMobileViewModelModule.FACTORY_NAME)
        private val confirmAbortToMobileWebViewModelFactory: ViewModelProvider.Factory,
        @Named(ConfirmAbortDesktopViewModelModule.FACTORY_NAME)
        private val confirmAbortToDesktopWebViewModelFactory: ViewModelProvider.Factory,
        @Named(AbortedReturnToDesktopWebViewModelModule.FACTORY_NAME)
        private val abortedReturnToDesktopWebViewModelFactory: ViewModelProvider.Factory,
        private val webNavigator: WebNavigator,
    ) : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(
            navController: NavController,
            onFinish: () -> Unit,
        ) {
            composable<AbortDestinations.ConfirmAbortMobile> {
                ConfirmAbortMobileScreen(
                    viewModel = viewModel(factory = confirmAbortToMobileWebViewModelFactory),
                    webNavigator = webNavigator,
                    onFinish = onFinish,
                )
            }

            composable<AbortDestinations.ConfirmAbortDesktop> {
                ConfirmAbortDesktopWebScreen(
                    viewModel = viewModel(factory = confirmAbortToDesktopWebViewModelFactory),
                    navController = navController,
                )
            }

            composable<AbortDestinations.AbortedReturnToDesktopWeb> {
                AbortedReturnToDesktopWebScreen(
                    viewModel = viewModel(factory = abortedReturnToDesktopWebViewModelFactory),
                )
            }
        }
    }
