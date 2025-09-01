package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.IdCheckWrapperAnalytics
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object SyncIdCheckViewModelModule {
    const val FACTORY_NAME = "SyncIdCheckViewModelModuleFactory"

    @Suppress("LongParameterList")
    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        configStore: ConfigStore,
        sessionStore: SessionStore,
        idCheckSdkActiveStateStore: IdCheckSdkActiveStateStore,
        launcherDataReader: LauncherDataReader,
        logger: Logger,
        analytics: IdCheckWrapperAnalytics,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                SyncIdCheckViewModel(
                    configStore = configStore,
                    sessionStore = sessionStore,
                    idCheckSdkActiveStateStore = idCheckSdkActiveStateStore,
                    launcherDataReader = launcherDataReader,
                    logger = logger,
                    analytics = analytics,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
}
