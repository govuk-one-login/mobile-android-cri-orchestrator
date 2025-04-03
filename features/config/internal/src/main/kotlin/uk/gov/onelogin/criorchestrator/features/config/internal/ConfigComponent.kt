package uk.gov.onelogin.criorchestrator.features.config.internal

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Singleton

@Singleton
@ContributesTo(CriOrchestratorSingletonScope::class)
interface ConfigComponent {
    fun configStore(): ConfigStore
}
