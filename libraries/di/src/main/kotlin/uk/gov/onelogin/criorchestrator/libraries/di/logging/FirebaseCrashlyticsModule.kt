package uk.gov.onelogin.criorchestrator.libraries.di.logging

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
object FirebaseCrashlyticsModule {
    @Provides
    fun providesCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
}
