package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortdesktopweb.ConfirmAbortDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortdesktopweb.ConfirmAbortDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktopweb.ConfirmAbortReturnToDesktopWebScreen
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktopweb.ConfirmAbortReturnToDesktopWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmaborttomobileweb.ConfirmAbortToMobileWeb
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmaborttomobileweb.ConfirmAbortToMobileWebViewModelModule
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ContributesMultibinding(CriOrchestratorScope::class)
class AbortNavGraphProvider
@Inject
constructor(
    @Named(ConfirmAbortToMobileWebViewModelModule.FACTORY_NAME)
    private val confirmAbortToMobileWebViewModelFactory: ViewModelProvider.Factory,
    @Named(ConfirmAbortDesktopWebViewModelModule.FACTORY_NAME)
    private val confirmAbortToDesktopWebViewModelFactory: ViewModelProvider.Factory,
    @Named(ConfirmAbortReturnToDesktopWebViewModelModule.FACTORY_NAME)
    private val confirmAbortReturnToDesktopWebViewModelFactory: ViewModelProvider.Factory,
    private val webNavigator: WebNavigator,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
        composable<AbortDestinations.ConfirmAbortMobile> {
            ConfirmAbortToMobileWeb(
                viewModel = viewModel(factory = confirmAbortToMobileWebViewModelFactory),
                webNavigator = webNavigator,
            )
        }

        composable<AbortDestinations.ConfirmAbortDesktop> {
            ConfirmAbortDesktopWebScreen(
                viewModel = viewModel(factory = confirmAbortToDesktopWebViewModelFactory),
                navController = navController,
            )
        }

        composable<AbortDestinations.ConfirmAbortReturnDesktop> {
            ConfirmAbortReturnToDesktopWebScreen(
                viewModel = viewModel(factory = confirmAbortReturnToDesktopWebViewModelFactory),
            )
        }
    }

}