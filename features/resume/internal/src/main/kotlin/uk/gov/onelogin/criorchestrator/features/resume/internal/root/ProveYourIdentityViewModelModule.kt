package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
object ProveYourIdentityViewModelModule {
    const val FACTORY_NAME = "ProveYourIdentityViewModelFactory"

    @Provides
    @Named(FACTORY_NAME)
    fun provideFactory(
        resumeAnalytics: ResumeAnalytics,
        refreshActiveSession: RefreshActiveSession,
        isSessionResumable: IsSessionResumable,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                ProveYourIdentityViewModel(
                    analytics = resumeAnalytics,
                    isSessionResumable = isSessionResumable,
                    refreshActiveSession = refreshActiveSession,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
}
