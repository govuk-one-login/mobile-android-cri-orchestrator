package uk.gov.onelogin.criorchestrator.libraries.di.logging

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import uk.gov.logging.api.Logger
import uk.gov.logging.impl.AndroidLogger
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
interface AndroidLoggerModule {
    @Binds
    fun bindsAndroidLogger(logger: AndroidLogger): Logger
}
