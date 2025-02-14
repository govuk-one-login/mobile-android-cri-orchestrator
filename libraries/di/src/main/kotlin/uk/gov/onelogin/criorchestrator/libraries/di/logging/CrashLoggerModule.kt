package uk.gov.onelogin.criorchestrator.libraries.di.logging

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.impl.CrashlyticsLogger
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
interface CrashLoggerModule {
    @Binds
    fun bindsCrashLogger(logger: CrashlyticsLogger): CrashLogger
}
