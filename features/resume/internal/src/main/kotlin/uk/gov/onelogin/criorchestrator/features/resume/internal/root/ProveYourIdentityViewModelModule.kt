package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSession
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@Module
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
